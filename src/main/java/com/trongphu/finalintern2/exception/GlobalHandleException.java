package com.trongphu.finalintern2.exception;

import com.trongphu.finalintern2.config.i18n.Translator;
import com.trongphu.finalintern2.exception.file.FileExceededSizeException;
import com.trongphu.finalintern2.exception.file.InvalidFileTypeException;
import com.trongphu.finalintern2.objectshttp.ResponseErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 17/09/2024 23:03
 * <br>
 * Class bắt lỗi toàn cục cho ứng dụng
 *
 * @author Trong Phu
 */
@ControllerAdvice
public class GlobalHandleException {
    private ResponseErrorObject setResponseErrorObject(WebRequest request, String message) {
        return ResponseErrorObject
                .builder()
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .path(request.getContextPath())
                .build();
    }

    /**
     * Bắt exception {@link FileExceededSizeException} file uploads quá kích thước quy định
     */
    @ExceptionHandler(FileExceededSizeException.class)
    public ResponseEntity<ResponseErrorObject> handleFileExceededSizeException(FileExceededSizeException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC FileExceededSizeException]");
        String message = Translator.toLocale(e.getMessage(), new Object[]{e.getSize()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link InvalidFileTypeException} file không đúng định dạng quy định
     */
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<ResponseErrorObject> handleInvalidFileTypeException(InvalidFileTypeException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC InvalidFileTypeException]");
        String message = Translator.toLocale(e.getMessage(), new Object[]{e.getFileType()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link DuplicateCodeEntityException} mã đã tồn tại
     */
    @ExceptionHandler(DuplicateCodeEntityException.class)
    public ResponseEntity<ResponseErrorObject> handleDuplicateCodeEntityException(DuplicateCodeEntityException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC DuplicateCodeEntityException]");
        String message = Translator.toLocale(e.getMessage(), new Object[]{e.getCode()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link InvalidArgumentException} đối số không hợp lệ cho 1 tham số nhất định
     */
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ResponseErrorObject> handleInvalidArgumentException(InvalidArgumentException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC InvalidArgumentException]");
        String message = Translator.toLocale(e.getMessage(), new Object[]{e.getArgumentValue(), e.getArgumentName()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link ResourceNotFoundException} không tìm thấy tài nguyên
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseErrorObject> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC ResourceNotFoundException]");
        String message = Translator.toLocale(e.getMessage(), new Object[]{e.getNameResource()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }

    /**
     * Bắt exception {@link MethodArgumentTypeMismatchException} đối số không hợp lệ cho 1 tham số nhất định
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MethodArgumentTypeMismatchException]");
        String message = Translator.toLocale("exception.MethodArgumentTypeMismatchException", new Object[]{e.getValue(), e.getName()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }
/**
     * Bắt exception {@link MissingPathVariableException} thiếu path variable
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ResponseErrorObject> handleMissingPathVariableException(MissingPathVariableException e, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MissingPathVariableException]");
        String message = Translator.toLocale("exception.MissingPathVariableException", new Object[]{e.getVariableName()});
        ResponseErrorObject errorObject = setResponseErrorObject(request, message);
        return  ResponseEntity.badRequest().body(errorObject);
    }


}
