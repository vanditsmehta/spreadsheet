package com.orkes.assignment.spreadsheet.sheet.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import com.orkes.assignment.spreadsheet.exceptions.DivideByZeroException;
import com.orkes.assignment.spreadsheet.exceptions.InvalidExpressionException;
import com.orkes.assignment.spreadsheet.sheet.repositories.Sheet;
import com.orkes.assignment.spreadsheet.sheet.validations.Validator;

public class ArithmeticExpression implements Expression {

    public static final String expressionRegex = "((([A-Z]+[1-9][0-9]*)|[0-9]+)[\\+\\-\\*/]?)*(([A-Z]+[1-9][0-9]*)|[0-9]+)";

    private String expression;

    private List<String> postfixExpression;

    private Set<String> dependencies;

    public ArithmeticExpression(String expression)
            throws InvalidExpressionException {
        this.expression = expression.trim().toUpperCase().substring(1,
                expression.length());
        this.parse();
    }

    public String getExpression() {
        return expression;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public List<String> getPostfixExpression() {
        return postfixExpression;
    }

    private void parse() throws InvalidExpressionException {
        String expWithoutBraces = this.expression.replaceAll("\\(", "")
                .replaceAll("\\)", "");
        if (!Pattern.matches(expressionRegex, expWithoutBraces)) {
            throw new InvalidExpressionException(
                    "Invalid Expression: " + this.expression);
        }
        infixToPostfix();
    }

    private void infixToPostfix() throws InvalidExpressionException {
        String[] params = this.expression
                .replaceAll("[\\+\\-\\*/\\(\\)]", " $0 ").trim().split("\\s+");
        Stack<String> operatorStack = new Stack<>();
        this.postfixExpression = new ArrayList<>();
        this.dependencies = new HashSet<>();
        for (String param : params) {
            if (isOperator(param)) {
                while (!operatorStack.isEmpty() && precedence(
                        param) <= precedence(operatorStack.peek())) {
                    postfixExpression.add(operatorStack.pop());
                }
                operatorStack.push(param);
            } else if (param.equals("(")) {
                operatorStack.push(param);
            } else if (param.equals(")")) {
                while (!operatorStack.isEmpty()) {
                    if (operatorStack.peek().equals("(")) {
                        break;
                    } else {
                        postfixExpression.add(operatorStack.pop());
                    }
                }
                if ("(".equals(operatorStack.peek())) {
                    operatorStack.pop();
                } else {
                    throw new InvalidExpressionException(
                            "Invalid Expression: " + this.expression);
                }
            } else if (param.isEmpty()) {
                throw new InvalidExpressionException(
                        "Invalid Expression: " + this.expression);
            } else {
                if (!Character.isDigit(param.charAt(0)))
                    dependencies.add(param);
                postfixExpression.add(param);
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().equals("(")) {
                throw new InvalidExpressionException(
                        "Invalid Expression: " + this.expression);
            }
            postfixExpression.add(operatorStack.pop());
        }
    }

    private boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*")
                || str.equals("/");
    }

    private int precedence(String operator) {
        if (operator.equals("+") || operator.equals("-"))
            return 1;
        else if (operator.equals("*") || operator.equals("/"))
            return 2;
        else
            return -1;
    }

    @Override
    public Integer evaluate(Sheet sheet) throws DivideByZeroException {
        int output = 0;
        Stack<Integer> stack = new Stack<>();
        for (String param : postfixExpression) {
            if (isOperator(param)) {
                stack.push(baseCalc(stack.pop(), stack.pop(), param));
            } else {
                if (Validator.validCellId(param)) {
                    Cell cell = sheet.findById(param);
                    stack.push(cell == null ? 0 : cell.getValue());
                } else {
                    stack.push(Integer.parseInt(param));
                }
            }
        }
        if (stack.size() == 1)
            output = stack.pop();
        return output;
    }

    private Integer baseCalc(Integer operand1, Integer operand2,
            String operator) throws DivideByZeroException {
        switch (operator) {
        case "+":
            return operand2 + operand1;
        case "-":
            return operand2 - operand1;
        case "*":
            return operand2 * operand1;
        case "/":
            if (operand1.equals(0)) {
                throw new DivideByZeroException(
                        "Encountered Division by Zero for expression: "
                                + this.expression);
            }
            return operand2 / operand1;
        }
        return 0;
    }
}
