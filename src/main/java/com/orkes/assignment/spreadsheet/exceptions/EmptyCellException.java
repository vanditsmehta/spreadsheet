package com.orkes.assignment.spreadsheet.exceptions;

public class EmptyCellException extends IllegalArgumentException {

    public EmptyCellException(String message) {
        super(message);
    }

}
