package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qinfeng
 * @date 2019/8/2 13:34
 */
@ApiModel(value = "ResultWrongAccount", description = "柜员满意度评价数据查询返回实体")
@Data
@NoArgsConstructor
public class ResultSatisfactionTeller {

    @ApiModelProperty(value = "列表数据")
    private PageInfo<SatisfactionTellerInfo> dataList;

    @ApiModelProperty(value = "图表数据")
    private List<SatisfactionTellerInfo> data;

}
