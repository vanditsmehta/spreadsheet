package com.orkes.assignment.spreadsheet.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(InvalidCellIdException.class)
        public ResponseEntity<?> handleInvalidCellIdException(
                        InvalidCellIdException ice, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                ice.getMessage(), ErrorCode.INVALID_CELL_ID);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InvalidCellValueException.class)
        public ResponseEntity<?> handleInvalidCellValueException(
                        InvalidCellValueException ice, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                ice.getMessage(), ErrorCode.INVALID_CELL_VALUE);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(UnsupportedCellValueType.class)
        public ResponseEntity<?> handleUnsupportedCellValueType(
                        UnsupportedCellValueType ucve, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                ucve.getMessage(),
                                ErrorCode.UNSUPPORTED_CELL_VALUE_TYPE);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(CyclicDependencyException.class)
        public ResponseEntity<?> handleCyclicDependencyException(
                        CyclicDependencyException cde, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                cde.getMessage(), ErrorCode.CYCLIC_DEPENDENCY);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InvalidExpressionException.class)
        public ResponseEntity<?> handleInvalidExpressionException(
                        InvalidExpressionException iee, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                iee.getMessage(), ErrorCode.INVALID_EXPRESSION);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(DivideByZeroException.class)
        public ResponseEntity<?> handleDivideByZeroException(
                        DivideByZeroException dbze, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                dbze.getMessage(), ErrorCode.DIVIDE_BY_ZERO);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(EmptyCellException.class)
        public ResponseEntity<?> handleEmptyCellException(
                        EmptyCellException ece, WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(),
                                ece.getMessage(), ErrorCode.EMPTY_CELL);
                return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleUnhandledException(Exception e,
                        WebRequest request) {
                ErrorInfo errorInfo = new ErrorInfo(new Date(), e.getMessage(),
                                ErrorCode.UNKNOWN_ERROR);
                return new ResponseEntity<>(errorInfo,
                                HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
