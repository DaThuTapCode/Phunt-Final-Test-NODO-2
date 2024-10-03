package com.trongphu.finalintern2.exception;

import com.trongphu.finalintern2.config.i18n.Translator;
import com.trongphu.finalintern2.exception.file.FileExceededSizeException;
import com.trongphu.finalintern2.exception.file.FileUploadErrorException;
import com.trongphu.finalintern2.exception.file.InvalidFileTypeException;
import com.trongphu.finalintern2.objectshttp.ResponseErrorObject;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

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
            MissingPathVariableException.class,
            NotIntegerException.class,
            FileUploadErrorException.class
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
            return ResponseEntity.status(404).body(errorObject);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            message = Translator.toLocale("exception.MethodArgumentTypeMismatchException", new Object[]{((MethodArgumentTypeMismatchException) e).getValue(), ((MethodArgumentTypeMismatchException) e).getName()});
        } else if (e instanceof MissingPathVariableException) {
            message = Translator.toLocale("exception.MissingPathVariableException", new Object[]{((MissingPathVariableException) e).getVariableName()});
        } else if (e instanceof NotIntegerException) {
            message = Translator.toLocale(e.getMessage());
        }else if (e instanceof FileUploadErrorException) {
            message = "Lỗi file";
        } else {
            message = "An unexpected error occurred";
        }

        ResponseErrorObject errorObject = createResponseError(request, status, message);
        return ResponseEntity.status(status).body(errorObject);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = Translator.toLocale(error.getDefaultMessage());
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.badRequest().body(errors);
//    }


    /**
     * Bắt exception {@link MethodArgumentNotValidException} validation đối tượng
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println("[-----> ĐÃ BẮT ĐƯỢC MethodArgumentNotValidException]");

        // Lấy lỗi đầu tiên từ danh sách lỗi
        FieldError firstFieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);

        if (firstFieldError != null) {
            String fieldErrorName = firstFieldError.getField(); // Tên trường lỗi
            String errorCode = firstFieldError.getCode(); // Loại mã lỗi

            System.out.println("[FIELD ERROR: " + fieldErrorName + "]");
            System.out.println("[ERROR CODE: " + errorCode + "]");

            String errorMessage = "";
            switch (errorCode) {
                case "NotBlank", "Email", "NotNull":
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName});
                    break;
                case "Range", "Length":
                    Object min = firstFieldError.getArguments().length > 2 ? firstFieldError.getArguments()[2] : "";
                    Object max = firstFieldError.getArguments().length > 1 ? firstFieldError.getArguments()[1] : "";
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName, min, max});
                    break;
                case "Min":
                    errorMessage = Translator.toLocale(errorCode, new Object[]{fieldErrorName, firstFieldError.getArguments()[1]});
                    break;
                case "Pattern":
                    errorMessage = Translator.toLocale(firstFieldError.getDefaultMessage());
                    break;
                case "Max":
                    errorMessage = Translator.toLocale(firstFieldError.getDefaultMessage());
                    break;
                default:
                    errorMessage = Translator.toLocale("exception.DataInputNotValid", new Object[]{fieldErrorName});
                    break;
            }

            ResponseErrorObject object = ResponseErrorObject.builder()
                    .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(errorMessage)
                    .path(request.getDescription(false).replace("uri=", ""))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.badRequest().body(object);
        }

        // Trường hợp không tìm thấy lỗi nào
        return ResponseEntity.badRequest().build();
    }


}
