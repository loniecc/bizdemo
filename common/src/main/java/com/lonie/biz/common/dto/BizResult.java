package com.lonie.biz.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author huzeming Created time 2020/3/16 : 1:37 下午 Desc:
 */

@Data
@Accessors(chain = true)
public class BizResult<T> {

    public BizResult(T data) {
        this.data = data;
    }

    public BizResult() {
    }

    String resultCode;
    String resultMsg;
    Boolean success;
    T data;

}
