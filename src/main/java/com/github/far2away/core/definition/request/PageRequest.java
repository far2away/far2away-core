package com.github.far2away.core.definition.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author far2away
 * @since 2021/10/15
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "通用分页请求")
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 6376944349189660267L;

    @ApiModelProperty(value = "当前页码", example = "1", position = -10)
    private int page = 1;

    @ApiModelProperty(value = "分页大小", example = "10", position = -9)
    private int size = 10;

}
