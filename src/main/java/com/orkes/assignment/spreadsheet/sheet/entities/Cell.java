package com.orkes.assignment.spreadsheet.sheet.entities;

import java.util.Set;
import java.util.regex.Pattern;

import com.orkes.assignment.spreadsheet.exceptions.InvalidExpressionException;
import com.orkes.assignment.spreadsheet.sheet.repositories.Sheet;

/**
 * 
 */

public class Cell {

    private String cellId;

    private Integer value;

    private Expression expression;

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Expression getExpression() {
        return expression;
    }

    public Set<String> getDependencies() {
        if (this.expression != null)
            return this.expression.getDependencies();
        return null;
    }

    public void setExpression(String expression)
            throws InvalidExpressionException {
        this.expression = new ArithmeticExpression(expression);
    }

    public static boolean validCellId(String cellId) {
        return Pattern.matches("[A-Z]+[0-9]+", cellId);
    }

    public void evaluate(Sheet sheet) {
        this.value = this.expression.evaluate(sheet);
    }

}
