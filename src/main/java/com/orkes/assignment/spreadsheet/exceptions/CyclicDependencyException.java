package com.orkes.assignment.spreadsheet.exceptions;

public class CyclicDependencyException extends IllegalArgumentException {

    public CyclicDependencyException(String message) {
        super(message);
    }

}
