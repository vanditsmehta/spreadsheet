package com.orkes.assignment.spreadsheet.sheet.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orkes.assignment.spreadsheet.sheet.entities.Cell;
import com.orkes.assignment.spreadsheet.sheet.exceptions.CyclicDependencyException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.EmptyCellException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.InvalidCellValueException;
import com.orkes.assignment.spreadsheet.sheet.repositories.Sheet;
import com.orkes.assignment.spreadsheet.sheet.repositories.Triggers;
import com.orkes.assignment.spreadsheet.sheet.validations.Validator;

@Service
public class SheetService implements ISheetService {

    @Autowired
    Sheet sheet;

    @Autowired
    Triggers triggers;

    @Override
    public Integer getCellValue(String cellId) throws EmptyCellException {
        Cell cell = sheet.findById(cellId);
        if (cell == null) {
            throw new EmptyCellException(
                    "Cell with ID: " + cellId + " doesn't exist");
        }
        return cell.getValue();
    }

    @Override
    public void setCellValue(String cellId, String value)
            throws CyclicDependencyException, InvalidCellValueException {
        Cell cell = new Cell();
        cell.setCellId(cellId);
        if (Validator.isIntegerValue(value)) {
            cell.setValue(Integer.parseInt(value));
        } else if (Validator.isExpressionValue(value)) {
            cell.setExpression((String) value);
            cell.evaluate(this.sheet);
            Cell existingCell = sheet.findById(cellId);
            if (existingCell != null)
                this.triggers.remove(existingCell.getDependencies(), cellId);
            try {
                triggers.save(cell.getDependencies(), cellId);
            } catch (CyclicDependencyException e) {
                triggers.remove(cell.getDependencies(), cellId);
                if (existingCell != null)
                    triggers.save(existingCell.getDependencies(), cellId);
                throw e;
            }
        } else {
            throw new InvalidCellValueException(
                    "Cell value : " + value + " is not valid");
        }
        sheet.save(cellId, cell);
        evaluateDependentCells(cellId);
    }

    private void evaluateDependentCells(String cellId) {
        Set<String> dependents = triggers.findById(cellId);
        if (dependents == null)
            return;
        for (String dependent : dependents) {
            evaluateDependentCells(dependent);
            Cell cell = sheet.findById(dependent);
            if (cell != null) {
                cell.evaluate(sheet);
            }
        }
    }

}
