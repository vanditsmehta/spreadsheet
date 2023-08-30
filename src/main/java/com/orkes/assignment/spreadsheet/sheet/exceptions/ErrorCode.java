package com.orkes.assignment.spreadsheet.sheet.exceptions;

public enum ErrorCode {

    INVALID_CELL_ID(411), INVALID_CELL_VALUE(412),
    UNSUPPORTED_CELL_VALUE_TYPE(413), EMPTY_CELL(414), INVALID_EXPRESSION(415),
    CYCLIC_DEPENDENCY(416), DIVIDE_BY_ZERO(417), UNKNOWN_ERROR(410);

    private final int code;

    private ErrorCode(int code) {
        this.code = code;
    }

    public int getErrorCode() {
        return code;
    }

}
