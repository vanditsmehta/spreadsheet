package com.orkes.assignment.spreadsheet.sheet.validations;

import java.util.regex.Pattern;

import com.orkes.assignment.spreadsheet.sheet.exceptions.InvalidCellIdException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.InvalidCellValueException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.UnsupportedCellValueType;

public class Validator {

    public static void validateCellId(String cellId) {
        if (!validCellId(cellId)) {
            throw new InvalidCellIdException("Invalid Cell ID: " + cellId);
        }
    }

    public static boolean validCellId(String cellId) {
        return Pattern.matches("[A-Z]+[0-9]+", cellId);
    }

    public static <T> void validateCellValue(T value) {
        String className = value.getClass().getSimpleName();
        switch (className) {
        case "String":
            String stringValue = ((String) value).trim();
            if (isExpressionValue(stringValue) || isIntegerValue(stringValue))
                return;
            else
                throw new InvalidCellValueException(
                        "Invalid Cell Value: " + stringValue);
        case "Integer":
            return;
        default:
            throw new UnsupportedCellValueType("Unsupported Value of type: "
                    + value.getClass().getSimpleName());
        }
    }

    public static boolean isExpressionValue(String value) {
        return value.startsWith("=");
    }

    public static boolean isIntegerValue(String value) {
        return value.chars().allMatch(Character::isDigit);
    }

}
