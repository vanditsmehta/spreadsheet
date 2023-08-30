package com.orkes.assignment.spreadsheet.sheet.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorInfo {
    private Date date;
    private String message;
    private ErrorCode statusCode;

    public ErrorInfo(Date date, String message, ErrorCode statusCode) {
        this.date = date;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode.getErrorCode();
    }

    public void setStatusCode(ErrorCode statusCode) {
        this.statusCode = statusCode;
    }
}
