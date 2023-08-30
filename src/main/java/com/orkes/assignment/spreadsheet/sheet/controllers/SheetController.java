package com.orkes.assignment.spreadsheet.sheet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orkes.assignment.spreadsheet.sheet.exceptions.EmptyCellException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.InvalidCellIdException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.InvalidCellValueException;
import com.orkes.assignment.spreadsheet.sheet.exceptions.UnsupportedCellValueType;
import com.orkes.assignment.spreadsheet.sheet.services.ISheetService;
import com.orkes.assignment.spreadsheet.sheet.validations.Validator;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/sheet")
public class SheetController {

    @Autowired
    private ISheetService sheetService;

    @GetMapping("/ping")
    public String ping() {
        return "Sheet Controller is up and reachable!";
    }

    @Operation(summary = "Set value for Cell with cellId")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request") })
    @PostMapping(value = "/{cellId}", consumes = MediaType.TEXT_PLAIN_VALUE)
    public <T> ResponseEntity<?> setCellValue(
            @ApiParam(name = "cellId", example = "AB12", type = "String", value = "Cell IDs follow (ColumnName)(RowNumber) convention, where ColumnName is Capital Letter(s) and RowNumber is integer > 0", required = true) @PathVariable String cellId,
            @ApiParam(name = "value", example = "23 or =(A1+A2)*3", type = "String", value = "Cell Value can either be some integer value or an arithmetic expression with +,-,*,/ operators with (round brackets).", required = true) @RequestBody String value)
            throws InvalidCellIdException, InvalidCellValueException,
            UnsupportedCellValueType {
        Validator.validateCellId(cellId);
        Validator.validateCellValue(value);
        sheetService.setCellValue(cellId, value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get value for Cell by cellId")
    @GetMapping("/{cellId}")
    public ResponseEntity<Integer> getCellValue(
            @ApiParam(name = "cellId", example = "AB12", type = "String", value = "Cell IDs follow (ColumnName)(RowNumber) convention, where ColumnName is Capital Letter(s) and RowNumber is integer > 0", required = true) @PathVariable String cellId)
            throws InvalidCellIdException, EmptyCellException {
        Validator.validateCellId(cellId);
        Integer cellValue = sheetService.getCellValue(cellId);
        return new ResponseEntity<Integer>(cellValue, HttpStatus.OK);
    }

}
