/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanier.easygrapher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Alex
 */
public class PostfixEvaluator {
    private final static String ADD = "+";
    private final static String SUBTRACT = "-";
    private final static String MULTIPLY = "*";
    private final static String DIVIDE = "/";
    private final static String EXPONENT = "^";

    public double evaluate(String[] expressionArr) {
        double operand1, operand2, result = 0.0;
        Stack<Double> stack = new Stack<>();

        for (String token : expressionArr) {
            if (token == null) {
                continue;
            }

            if (isOperator(token)) {
                operand2 = stack.pop();
                operand1 = stack.pop();

                result = switch (token) {
                    case ADD -> operand1 + operand2;
                    case SUBTRACT -> operand1 - operand2;
                    case MULTIPLY -> operand1 * operand2;
                    case DIVIDE -> operand1 / operand2;
                    default -> result;
                };

                stack.push(result);
            } else {
                try {
                    Double num = Double.valueOf(token);
                    stack.push(num);

                } catch (NumberFormatException e) {
                    System.out.println("not a double");
                }
            }
        }

        return result;
    }

    public String[] convertInfix(String expr) {
        if (expr == null || expr.isEmpty()) {
            return null;
        }

        ArrayList<String> postfixList = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            String currStr = String.valueOf(expr.charAt(i));
            char currChr = currStr.charAt(0);

            // if operand, add to postfix
            if ((currChr >= 'a' && currChr <= 'z') ||
                    (currChr >= 'A' && currChr <= 'Z') ||
                    (currChr >= '0' && currChr <= '9'))
                postfixList.add(currStr);

            else if (currChr == '(') {
                operatorStack.push(currStr);
            }

            // if closing bracket, pop everything until "("
            else if (currChr == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.pop();
            } else if (isOperator(currStr)) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") &&
                        // previous operator cannot be greater priority
                        (getOperatorPriority(operatorStack.peek()) > getOperatorPriority(currStr) ||
                                // two operators of the same priority cannot stay tgt
                                (getOperatorPriority(operatorStack.peek()) == getOperatorPriority(currStr) &&
                                        !isRightAssociative(currStr)
                                ))) {
                    postfixList.add(operatorStack.pop());
                }
                operatorStack.push(currStr);
            }
        }

        // pop remaining operators
        while (!operatorStack.isEmpty()) {
            postfixList.add(operatorStack.pop());
        }

        // catch exception and return as user input expr error

        return postfixList.toArray(new String[0]);
    }

    /**
     * Checks if the given string is a valid operator
     *
     * @param c the input String
     * @return whether the string is a valid operator
     */
    private boolean isOperator(String c) {
        if (c == null) {
            return false;
        }

        return switch (c) {
            case ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT,
                 "(", ")" -> true;
            default -> false;
        };
    }

    /**
     * Check if operator is right-associative
     *
     * @param operator the operator str
     * @return whether the operator is right-associative
     */
    private boolean isRightAssociative(String operator) {
        if (operator == null) {
            return false;
        }

        return switch (operator) {
            case EXPONENT, SUBTRACT -> true;
            default -> false;
        };
    }

    /**
     * Returns precedence of operators
     *
     * @param operator operator str
     * @return the precedence
     */
    private int getOperatorPriority(String operator) {
        return switch (operator) {
            case EXPONENT -> 3;
            case MULTIPLY, DIVIDE -> 2;
            case ADD, SUBTRACT -> 1;
            default -> -1;
        };
    }
}
