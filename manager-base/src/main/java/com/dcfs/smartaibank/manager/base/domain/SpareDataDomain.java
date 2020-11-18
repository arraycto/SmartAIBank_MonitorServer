package com.dcfs.smartaibank.manager.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**备选数据
 * @author wangtingo
 */
@ApiModel(value = "SpareDataDomain", description = "备选数据实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SpareDataDomain {

    @ApiModelProperty(value = "备选数据id")
    private String key;

    @ApiModelProperty(value = "备选数据值")
    private String value;
}
