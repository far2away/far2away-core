package com.github.far2away.core.definition.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.far2away.core.definition.i18n.I18nMessageDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Data
@JsonInclude
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用返回响应体")
public class ResponseVo<T> implements Serializable {

    private static final long serialVersionUID = -1096045286944269090L;

    @ApiModelProperty(value = "返回状态码", required = true, example = "00000", position = 1)
    private String code;

    @ApiModelProperty(value = "返回消息", required = true, dataType = "string", example = "成功", position = 2)
    private I18nMessageDetails msg;

    @ApiModelProperty(value = "语言环境", required = true, example = "zh_CN", position = 3)
    private String locale;

    @ApiModelProperty(value = "请求编号", required = true, example = "2119fc3e513f5b97", position = 4)
    private String traceId;

    @ApiModelProperty(value = "返回数据", required = true, position = 5)
    private T data;

}
