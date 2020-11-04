package com.nts.iot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * @author jie
 * @date 2018-11-23
 * 未授权日常处理
 */
@Getter
public class UnAuthorizedException extends RuntimeException {

    private Integer status = UNAUTHORIZED.value();

    public UnAuthorizedException(String msg) {
        super(msg);
    }

    public UnAuthorizedException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }
}
