/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanier.easygrapher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Alex
 */
public class PostfixEvaluator {
    private final static String UNARY_SUB = "~";
    private final static String ADD = "+";
    private final static String SUBTRACT = "-";
    private final static String MULTIPLY = "*";
    private final static String DIVIDE = "/";
    private final static String EXPONENT = "^";
    //TODO
    private final static String LN = "ln";
    private final static String SIN = "sin";
    private final static String COS = "cos";
    private final static String TAN = "tan";
    private final static String ASIN = "asin";
    private final static String ACOS = "acos";
    private final static String ATAN = "atan";
    private final static String SQRT = "sqrt";
    private final static String ABS = "abs";
    private final static List<String> operators = Arrays.asList(
            UNARY_SUB, ADD, SUBTRACT, MULTIPLY, DIVIDE,
            EXPONENT, LN,
            SIN, COS, TAN,
            ASIN, ACOS, ATAN,
            SQRT, ABS
    );

    /*
    TODO
    include a total of
    + - * / ^
ln
sin cos tan
asin acos atan
sqrt abs
pi e

     */

    public double evaluatePostfix(String[] expressionArr) {
        double operand1, operand2, result = 0.0;
        Stack<Double> stack = new Stack<>();

        for (String token : expressionArr) {
            if (token == null) {
                continue;
            }

            if (isOperator(token)) {
                if (token.equals(UNARY_SUB)) {
                    operand1 = stack.pop();
                    result = -operand1;
                } else {
                    operand2 = stack.pop();
                    operand1 = stack.pop();

                    result = switch (token) {
                        case ADD -> operand1 + operand2;
                        case SUBTRACT -> operand1 - operand2;
                        case MULTIPLY -> operand1 * operand2;
                        case DIVIDE -> operand1 / operand2;
                        case EXPONENT -> Math.pow(operand1, operand2);
                        default -> result;
                    };
                }

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

    public String[] convertInfixToArr(String expr) {
        if (expr == null || expr.isEmpty()) {
            return null;
        }

        ArrayList<String> arrayList = new ArrayList<>();
        String prevStr = "";
        boolean isPrevNum = false;
        for (int i = 0; i < expr.length(); i++) {
            String currStr = String.valueOf(expr.charAt(i));
            char currChr = currStr.charAt(0);

            if (currStr.isBlank()) {
                continue;
            } else if ((currChr >= 'a' && currChr <= 'z') ||
                    (currChr >= 'A' && currChr <= 'Z') ||
                    (currChr >= '0' && currChr <= '9') ||
                    currChr == '.') {
                if (isPrevNum) {
                    String newNum = arrayList.getLast() + currChr;
                    arrayList.set(arrayList.size() - 1, newNum);
                } else {
                    arrayList.add(currStr);
                }
                isPrevNum = true;
            } else {
                isPrevNum = false;
                if (currChr == '-' || currChr == '+') {
                    // '-' is a negation when minus is at beginning of expression
                    // or after an opening parenthesis
                    // or after binary operator
                    // then it must be unary negation '~'
                    if (i == 0 || prevStr.equals("(") || isOperator(prevStr)) {
                        if (currChr == '-') {
                            arrayList.add("~");
                        }
                        // no need to add unary '+'
                        continue;
                    }
                }
                arrayList.add(currStr);
            }

            prevStr = currStr;
        }

        return arrayList.toArray(new String[0]);
    }

    public String[] convertInfixToPost(String exprStr) {
        if (exprStr == null || exprStr.isEmpty()) {
            return null;
        }
        String[] expr = convertInfixToArr(exprStr);

        ArrayList<String> postfixList = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();

        for (int i = 0; i < expr.length; i++) {
            String currStr = String.valueOf(expr[i]);
            char currChr = currStr.charAt(0);

            // if operand, add to postfix
            if ((currChr >= 'a' && currChr <= 'z') ||
                    (currChr >= 'A' && currChr <= 'Z') ||
                    (currChr >= '0' && currChr <= '9') ||
                    currChr == '.'
            ) {
                postfixList.add(currStr);
            } else if (currChr == '(') {
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
                        (getOperatorPrecedence(operatorStack.peek()) > getOperatorPrecedence(currStr) ||
                                // two operators of the same priority cannot stay tgt
                                (getOperatorPrecedence(operatorStack.peek()) == getOperatorPrecedence(currStr) &&
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

        return operators.contains(c);
    }

    private boolean isParenthesis(String c) {
        if (c == null) {
            return false;
        }

        return c.equals("(") || c.equals(")");
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
            case EXPONENT, UNARY_SUB -> true;
            default -> false;
        };
    }

    /**
     * Returns precedence of operators
     *
     * @param operator operator str
     * @return the precedence
     */
    private int getOperatorPrecedence(String operator) {
        return switch (operator) {
            case UNARY_SUB -> 4;
            case EXPONENT -> 3;
            case MULTIPLY, DIVIDE -> 2;
            case ADD, SUBTRACT -> 1;
            default -> -1;
        };
    }
}
