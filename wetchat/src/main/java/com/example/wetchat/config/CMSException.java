package com.example.wetchat.config;

import lombok.Data;

@Data
public class CMSException extends RuntimeException {
    private Integer code;

    public CMSException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CMSException(HttpStatusEnum httpStatusEnum) {
        super(httpStatusEnum.reasonPhraseCN());
        this.code = httpStatusEnum.code();
    }

    @Override
    public String toString() {
        return "CMSException{" + "code=" + code + ",message=" + this.getMessage() + "}";
    }
}
