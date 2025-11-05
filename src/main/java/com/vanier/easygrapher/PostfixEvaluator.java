/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vanier.easygrapher;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Alex
 */
public class PostfixEvaluator {
    private final static String ADD = "+";
    private final static String SUBTRACT = "-";
    private final static String MULTIPLY = "*";
    private final static String DIVIDE = "/";
    
    private Stack<Double> stack;

    public PostfixEvaluator() {
        stack = new Stack<>();
    }
    
    public double evaluate(String[] expressionArr) {
        Double operand1, operand2, result = 0.0;
        
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
            }
            
            else {
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
    
    public boolean isOperator(String c) {
        if (c == null) {
            return false;
        }
        
        return c.equals(ADD) || c.equals(MULTIPLY) || c.equals(SUBTRACT) || c.equals(DIVIDE);
    }
}
