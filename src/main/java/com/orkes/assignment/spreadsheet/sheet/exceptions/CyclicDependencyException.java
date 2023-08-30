package com.orkes.assignment.spreadsheet.sheet.exceptions;

public class CyclicDependencyException extends IllegalArgumentException {

    public CyclicDependencyException(String message) {
        super(message);
    }

}
