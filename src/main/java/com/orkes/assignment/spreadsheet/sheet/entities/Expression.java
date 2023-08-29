package com.orkes.assignment.spreadsheet.sheet.entities;

import java.util.Set;

import com.orkes.assignment.spreadsheet.sheet.repositories.Sheet;

public interface Expression {

    public Integer evaluate(Sheet sheet) throws ArithmeticException;

    public String getExpression();

    public Set<String> getDependencies();

}
