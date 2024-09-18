package com.trongphu.finalintern2.exception;

/**
 * Created by Trong Phu on 18/09/2024 14:32
 *
 * @author Trong Phu
 */
public class DuplicateCodeEntityException extends RuntimeException {
    private String code;

    public DuplicateCodeEntityException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }
}
