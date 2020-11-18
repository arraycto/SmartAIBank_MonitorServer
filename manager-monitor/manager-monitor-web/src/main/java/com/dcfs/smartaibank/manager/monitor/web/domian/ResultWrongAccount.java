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
@ApiModel(value = "ResultWrongAccount", description = "错账统计速度查询返回实体")
@Data
@NoArgsConstructor
public class ResultWrongAccount {

    @ApiModelProperty(value = "列表数据")
    private PageInfo<HistoryErrotAccountInfo> dataList;

    @ApiModelProperty(value = "图表数据")
    private List<HistoryErrotAccountInfo> data;

}
