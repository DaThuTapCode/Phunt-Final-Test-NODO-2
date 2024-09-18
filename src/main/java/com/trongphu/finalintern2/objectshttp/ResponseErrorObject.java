package com.trongphu.finalintern2.objectshttp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 18/09/2024 09:24
 *
 * @author Trong Phu
 */
@Getter
@Setter
@Builder
public class ResponseErrorObject {

    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String path;

    private String message;

}
