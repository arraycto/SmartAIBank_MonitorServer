package com.dcfs.smartaibank.manager.monitor.web.util.excelutil;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.file.excel.ExcelExportException;
import com.dcfs.smartaibank.file.excel.ExcelExportParams;
import com.dcfs.smartaibank.file.excel.ExcelExportUtil;
import com.dcfs.smartaibank.manager.monitor.web.constance.ExcelFieldConstants;
import com.dcfs.smartaibank.manager.monitor.web.domian.HeaderColInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控excel使用模板方式导出类
 *
 * @author wangjzm
 * @data 2019/12/3
 * @since 1.0.0
 */
@Slf4j
public final class ExcelUtilForMonitor {
    private ExcelUtilForMonitor() {
    }

    /**
     * 获取只带有一级简单表头的excel流
     *
     * @param list              数据集合
     * @param templateUrl       模板文件路径
     * @param title             表格标题
     * @param statisticalPeriod 表格中统计时段
     * @param <T>               数据类型
     * @return InputStream
     * @throws ExcelExportException
     */
    public static <T> InputStream createExcelFilesWithFirstLevelHeader(List<T> list,
                                                                       String templateUrl,
                                                                       String title,
                                                                       String statisticalPeriod)
            throws ExcelExportException {
        Map<String, Object> value = new HashMap<>(16);
        // 1.map中首先放入dataList
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            if (list != null) {
                for (T row : list) {
                    Map<String, Object> map = new HashMap<>(16);
                    map = BeanUtils.describe(row);
                    dataList.add(map);
                }
            }
            value.put("dataList", dataList);
        } catch (Exception e) {
            log.error("{}-报表导出对象转map异常", title, e);
            throw new BusinessException("object.conversion.map.error", e);
        }
        // 2.map放置title和时间段信息
        addTitleAndTime(value, title, statisticalPeriod);
        return ExcelExportUtil.export(templateUrl, value);
    }

    /**
     * 往map中增加表格标题和统计时段
     *
     * @param value             map,包括了数据集合、表格标题和统计时段
     * @param title             表格标题
     * @param statisticalPeriod 统计时段
     */
    private static void addTitleAndTime(Map<String, Object> value, String title, String statisticalPeriod) {
        String statisticsTime = new SimpleDateFormat(ExcelFieldConstants.DATE_CHINESE_FORMAT).format(new Date());
        value.put(ExcelFieldConstants.TITLE, title);
        value.put(ExcelFieldConstants.STATISTICS_PERIOD, statisticalPeriod);
        value.put(ExcelFieldConstants.STATISTICS_TIME, statisticsTime);
    }

    /**
     * 获取只带有二级复杂表头的excel
     *
     * @param list              数据集合
     * @param templateUrl       模板文件路径
     * @param title             表格标题
     * @param statisticalPeriod 表格中统计时段
     * @param headerColInfos    二级表头信息
     * @return WorkBook
     */
    public static <T, U> InputStream createExcelFilesWithSecondLevelHeader(List<T> list,
                                                                           String templateUrl,
                                                                           String title,
                                                                           String statisticalPeriod,
                                                                           List<HeaderColInfo> headerColInfos,
                                                                           List<U> secondaryList)
            throws Exception {
        ExcelExportParams params = new ExcelExportParams(templateUrl);
        params.setColForEach(true);
        Map<String, Object> value = new HashMap<>(16);
        List<Map<String, Object>> colList;
        List<Map<String, Object>> dataList;
        // 获取二级表头字段信息
        if (list != null && !list.isEmpty()) {
            colList = getSecondaryFieldsInfo(list, headerColInfos);
            dataList = getSecondaryDataList(list);
        } else {
            // 这里只去设置表头信息
            colList = getColList(secondaryList, headerColInfos);
            dataList = new ArrayList<>();
        }
        // 获取数据信息
        value.put("colList", colList);
        value.put("dataList", dataList);
        addTitleAndTime(value, title, statisticalPeriod);
        return ExcelExportUtil.export(params, value);
    }

    /**
     * 获取带有二级表头的dataList数据
     *
     * @param list 原始数据集合
     * @return 带有二级表头的数据
     */
    private static <T> List<Map<String, Object>> getSecondaryDataList(List<T> list) throws Exception {
        List<Map<String, Object>> dataList = new ArrayList<>(16);
        int secondHeaderIndex = 0;
        for (T t : list) {
            Field[] fields = t.getClass().getDeclaredFields();
            Map<String, Object> dataMap = new HashMap<>(16);
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                if (List.class.isAssignableFrom(field.getType())) {
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {
                        List db = getSecondaryDataList(field, type, t);
                        int size = getSecondaryDataListSize(db);
                        for (int i = 0; i < size; i++) {
                            Object obj = db.get(i);
                            Field[] objFields = obj.getClass().getDeclaredFields();
                            setMapByObjFields(dataMap, obj, objFields, Integer.toString(secondHeaderIndex));
                            secondHeaderIndex++;
                        }
                    }
                } else {
                    setMapBySingleField(dataMap, t, field, ExcelFieldConstants.BLANK);
                }
            }
            dataList.add(dataMap);
            secondHeaderIndex = 0;
        }
        return dataList;
    }

    private static void setMapByObjFields(Map<String, Object> map,
                                          Object obj,
                                          Field[] fields,
                                          String index) throws Exception {
        for (Field field : fields) {
            setMapBySingleField(map, obj, field, index);
        }
    }

    private static void setMapBySingleField(Map<String, Object> map,
                                            Object obj,
                                            Field field,
                                            String index) throws Exception {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        Method method = obj.getClass().getMethod(getFunctionName(fieldName));
        String result = getValueString(obj, method, fieldType.getName());
        map.put(fieldName + index, result);
    }

    private static <T> List getSecondaryDataList(Field field, Type type, T t) throws Exception {
        Method method1 = t.getClass().getMethod(getFunctionName(field.getName()));
        List db = (List) method1.invoke(t);
        return db;
    }

    private static int getSecondaryDataListSize(List data) {
        return data == null ? 0 : data.size();
    }

    private static <T> List<Map<String, Object>> getSecondaryFieldsInfo(List<T> list,
                                                                        List<HeaderColInfo> headerColInfos)
            throws Exception {
        List<Map<String, Object>> colList;
        // 二级表头的索引值，通过模板指令设置easypoi模板中非固定二级表头的表达式
        Object t = list.get(0);
        // 获取最外层字段信息
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            // 如果字段是List类型，从该字段去获取二级表头信息
            if (List.class.isAssignableFrom(field.getType())) {
                Type type = field.getGenericType();
                if (type instanceof ParameterizedType) {
                    List db = getSecondaryDataList(field, type, t);
                    colList = getColList(db, headerColInfos);
                    return colList;
                }
            }
        }
        return null;
    }

    private static List<Map<String, Object>> getColList(List secondaryList, List<HeaderColInfo> headerColInfos)
            throws Exception {
        List<Map<String, Object>> colList = new ArrayList<>();
        int secondHeaderIndex = 0;
        int size = getSecondaryDataListSize(secondaryList);
        for (int i = 0; i < size; i++) {
            int keyIndex = 0;
            Object obj = secondaryList.get(i);
            Field[] objFields = obj.getClass().getDeclaredFields();
            Map<String, Object> colMap = new HashMap<>(8);
            setMapByObjFields(colMap, obj, objFields, ExcelFieldConstants.BLANK);
            if (headerColInfos != null && !headerColInfos.isEmpty()) {
                for (HeaderColInfo headerColInfo : headerColInfos) {
                    String key = headerColInfo.getKey();
                    String value = headerColInfo.getValue();
                    colMap.put("header" + keyIndex, value);
                    colMap.put(key, "t." + key + secondHeaderIndex);
                    keyIndex++;
                }
            }
            colList.add(colMap);
            secondHeaderIndex++;
        }
        return colList;
    }

    private static String getFunctionName(String properties) {
        String toUpper = properties.substring(0, 1);
        String upper = toUpper.toUpperCase();
        return "get" + properties.replaceFirst(toUpper, upper);
    }

    /**
     * 生成数据存到excel [有新的类型，则需要添加进去]
     *
     * @param obj        数据对象
     * @param m          方法
     * @param typeString 类型
     * @return 返回结果
     */
    private static String getValueString(Object obj, Method m, String typeString) {
        try {
            Object object = m.invoke(obj);
            SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            switch (typeString) {
                case "java.lang.String":
                    return object == null ? null : (String) object;
                case "java.lang.Integer":
                    return object == null ? null : object.toString();
                case "java.lang.Double":
                    return object == null ? null : object.toString();
                case "java.lang.Float":
                    return object == null ? null : object.toString();
                case "java.math.BigDecimal":
                    return object == null ? null : object.toString();
                case "java.util.Date":
                    return object == null ? null : smf.format((Date) object);
                default:
                    return "";
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("数据类型转换异常:{},{}", e.getLocalizedMessage(), e.getMessage());
        }
        return "";
    }


}
