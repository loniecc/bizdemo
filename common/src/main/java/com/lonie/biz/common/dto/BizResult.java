package com.lonie.biz.common.dto;

import lombok.Data;

/**
 * @author huzeming Created time 2020/3/16 : 1:37 下午 Desc:
 */

@Data
public class BizResult<T> {

    String resultCode;
    String resultMsg;
    Boolean success;
    T data;

}
