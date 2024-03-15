package com.qmlx.usercenter.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用分页查请求参数
 */
@Data
public class PageRequest {


    @ApiModelProperty("页面大小")
    private int pageSize;

    @ApiModelProperty("第几页")
    private int pageNum;
}
