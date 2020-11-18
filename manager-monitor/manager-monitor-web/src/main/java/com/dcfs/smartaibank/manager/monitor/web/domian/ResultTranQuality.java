package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author tanchena
 * @date 2019/8/2 13:34
 */
@ApiModel(value = "ResultTranQuality", description = "交易质量统计速度查询返回实体")
@Data
@NoArgsConstructor
public class ResultTranQuality {

    @ApiModelProperty(value = "列表数据")
    private PageInfo<HistoryTransQualityInfo> dataList;

    @ApiModelProperty(value = "图表数据")
    private List<HistoryTransQualityInfo> data;

}
