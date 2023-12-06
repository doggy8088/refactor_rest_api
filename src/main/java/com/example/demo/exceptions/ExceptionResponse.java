package com.example.demo.exceptions;

import java.text.DateFormat;
import java.util.Date;

/**
 * All handled exceptions contains timestamp, message and its details.
 */
public class ExceptionResponse {
    private String timestamp;
    private String message;

    public ExceptionResponse(Date timestamp, String message) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        this.timestamp = dateFormat.format(timestamp);
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        this.timestamp = dateFormat.format(timestamp);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
