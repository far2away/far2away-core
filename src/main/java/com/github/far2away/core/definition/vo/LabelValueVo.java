package com.github.far2away.core.definition.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
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
@ApiModel(value = "LabelValue响应")
public class LabelValueVo<T, R> implements Serializable {

    private static final long serialVersionUID = 8936653380645871942L;

    @ApiModelProperty(value = "展示的值Label", required = true, example = "VIP", position = 1)
    private T label;

    @ApiModelProperty(value = "实际的值Value", required = true, example = "1", position = 2)
    private R value;

    @JsonInclude(Include.NON_EMPTY)
    @ApiModelProperty(value = "额外的信息", position = 3)
    private Map<String, Object> extData;

    public static <T, R> LabelValueVo<T, R> of(T label, R value) {
        return new LabelValueVo<>(label, value, Collections.emptyMap());
    }

    public static <T, R> LabelValueVo<T, R> of(T label, R value, Map<String, Object> extData) {
        return new LabelValueVo<>(label, value, extData);
    }

}
