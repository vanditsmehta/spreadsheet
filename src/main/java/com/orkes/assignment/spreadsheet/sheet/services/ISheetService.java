package com.orkes.assignment.spreadsheet.sheet.services;

import com.orkes.assignment.spreadsheet.exceptions.CyclicDependencyException;
import com.orkes.assignment.spreadsheet.exceptions.EmptyCellException;
import com.orkes.assignment.spreadsheet.exceptions.InvalidCellValueException;

public interface ISheetService {

    Integer getCellValue(String cellId) throws EmptyCellException;

    void setCellValue(String cellId, String value)
            throws CyclicDependencyException, InvalidCellValueException;

}