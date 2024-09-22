package com.trongphu.finalintern2.objectshttp;

/**
 * Created by Trong Phu on 18/09/2024 09:25
 *
 * @author Trong Phu
 */
public class ResponseDataObject<T> {
    private final int status;
    private final String message;

    private  T data;

    //    PUT, PATCH, DELETE
    public ResponseDataObject(int status, String message) {
        this.status = status;
        this.message = message;
    }

    //  GET, POST
    public ResponseDataObject(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
