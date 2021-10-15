package com.github.far2away.core.definition.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.far2away.core.util.generic.FunctionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
@ApiModel(value = "分页响应")
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 7147544394742495586L;

    @ApiModelProperty(value = "总页数", required = true, example = "10", position = 1)
    private int totalPages;

    @ApiModelProperty(value = "总条数", required = true, example = "97", position = 2)
    private long totalElements;

    @ApiModelProperty(value = "当前页内容", required = true, position = 3)
    private List<T> content;

    public <U> PageVo<U> map(Function<? super T, ? extends U> converter) {
        List<U> newContent = FunctionUtils.nullAsEmptyStream(content)
            .map(converter)
            .collect(Collectors.toList());
        return new PageVo<>(this.getTotalPages(), this.getTotalElements(), newContent);
    }

}
