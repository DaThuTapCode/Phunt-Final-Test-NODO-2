package com.trongphu.finalintern2.exception;

import com.trongphu.finalintern2.config.i18n.Translator;
import com.trongphu.finalintern2.exception.file.FileExceededSizeException;
import com.trongphu.finalintern2.exception.file.InvalidFileTypeException;
import com.trongphu.finalintern2.objectshttp.ResponseErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Trong Phu on 17/09/2024 23:03
 * <br>
 * Class bắt lỗi toàn cục cho ứng dụng
 *
 * @author Trong Phu
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseErrorObject createResponseError(WebRequest request, HttpStatus status, String message) {
        return ResponseErrorObject.builder()
                .timestamp(LocalDateTime.now())
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .path(request.getDescription(false))
                .build();
    }

    @ExceptionHandler({
                    FileExceededSizeException.class,
                    InvalidFileTypeException.class,
                    DuplicateCodeEntityException.class,
                    InvalidArgumentException.class,
                    ResourceNotFoundException.class,
                    MethodArgumentTypeMismatchException.class,
                    MissingPathVariableException.class
            })
    public ResponseEntity<ResponseErrorObject> handleException(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;

        if (e instanceof FileExceededSizeException) {
            message = Translator.toLocale(e.getMessage(), new Object[]{((FileExceededSizeException) e).getSize()});
        } else if (e instanceof InvalidFileTypeException) {
            message = Translator.toLocale(e.getMessage(), new Object[]{((InvalidFileTypeException) e).getFileType()});
        } else if (e instanceof DuplicateCodeEntityException) {
            message = Translator.toLocale(e.getMessage(), new Object[]{((DuplicateCodeEntityException) e).getCode()});
        } else if (e instanceof InvalidArgumentException) {
            message = Translator.toLocale(e.getMessage(), new Object[]{((InvalidArgumentException) e).getArgumentValue(), ((InvalidArgumentException) e).getArgumentName()});
        } else if (e instanceof ResourceNotFoundException) {
            message = Translator.toLocale(e.getMessage(), new Object[]{((ResourceNotFoundException) e).getNameResource()});
            ResponseErrorObject errorObject = createResponseError(request, HttpStatus.NOT_FOUND, message);
            errorObject.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
            return ResponseEntity.status(status).body(errorObject);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            message = Translator.toLocale("exception.MethodArgumentTypeMismatchException", new Object[]{((MethodArgumentTypeMismatchException) e).getValue(), ((MethodArgumentTypeMismatchException) e).getName()});
        } else if (e instanceof MissingPathVariableException) {
            message = Translator.toLocale("exception.MissingPathVariableException", new Object[]{((MissingPathVariableException) e).getVariableName()});
        } else {
            message = "An unexpected error occurred";
        }

        ResponseErrorObject errorObject = createResponseError(request, status, message);
        return ResponseEntity.status(status).body(errorObject);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = Translator.toLocale(error.getDefaultMessage());
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
