package com.trongphu.finalintern2.exception;

import lombok.Getter;

/**
 * Created by Trong Phu on 18/09/2024 16:23
 *
 * @author Trong Phu
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private String nameResource;

    public ResourceNotFoundException(String message, String nameResource) {
        super(message);
        this.nameResource = nameResource;
    }
}
