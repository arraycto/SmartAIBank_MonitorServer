package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tanchena
 * @date 2019/8/1 16:47
 */
@ApiModel(value = "ResponseData", description = "处理结果返回实体")
@Data
@NoArgsConstructor
public class ResponseData {

    @JsonProperty("RET_CODE")
    private String code;

    @JsonProperty("RET_INFO")
    private String info;

    @ApiModelProperty(value = "是否成功,true-成功,false-失败")
    private Boolean isSuccess;
}
