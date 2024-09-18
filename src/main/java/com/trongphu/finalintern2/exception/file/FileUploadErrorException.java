package com.trongphu.finalintern2.exception.file;

/**
 * Created by Trong Phu on 18/09/2024 14:11
 *
 * @author Trong Phu
 */
public class FileUploadErrorException extends RuntimeException{
    public FileUploadErrorException (String message){
        super(message);
    }
}
