package com.trongphu.finalintern2.exception;

import lombok.Getter;

/**
 * Created by Trong Phu on 18/09/2024 15:39
 *
 * @author Trong Phu
 */
@Getter
public class InvalidArgumentException extends RuntimeException{
    private String argumentName;
    private String argumentValue;

    public InvalidArgumentException(String message, String argumentName, String argumentValue){
        super(message);
        this.argumentName = argumentName;
        this.argumentValue = argumentValue;
    }
}
