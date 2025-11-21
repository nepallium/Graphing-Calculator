/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.vanier.easygrapher;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 * @author Alex
 */
public class PostfixEvaluatorTest {
    
    private PostfixEvaluator pfEval;
    
    @Before
    public void setUp() {
        pfEval = new PostfixEvaluator();
    }

    @Test
    public void testEvaluate_Postfix_regular() {
        String[] normal1 = {"3", "4", "+", "2", "*", "7", "/"};
        double result1 = pfEval.evaluatePostfix(normal1);
        assertEquals(2.0, result1, 0.001);
        
        String[] normal2 = {"4", "2", "+", "3", "5", "1", "-", "*", "+"};
        double result2 = pfEval.evaluatePostfix(normal2);
        assertEquals(18.0, result2, 0.001);

        String[] normal3 = {"7", "4", "-3", "*", "1", "5", "+", "/", "*"};
        double result3 = pfEval.evaluatePostfix(normal3);
        assertEquals(-14.0, result3, 0.001);
    }

    @Test
    public void testEvaluate_Postfix_negativeFactor() {
        String[] normal1 = {"3", "~", "4", "~", "~", "~", "*"};
        double result1 = pfEval.evaluatePostfix(normal1);
        assertEquals("12", 12.0, result1, 0.001);

        String[] normal2 = {"7", "4", "3", "~", "*", "*", "5", "1", "+", "/"};
        double result2 = pfEval.evaluatePostfix(normal2);
        assertEquals("-14",-14.0, result2, 0.001);

        String[] normal3 = {"2", "5", "~", "7", "-", "*"};
        double result3 = pfEval.evaluatePostfix(normal3);
        assertEquals("-24", -24.0, result3, 0.001);

        String[] normal4 = {"4", "3", "*", "8", "-"};
        double result4 = pfEval.evaluatePostfix(normal4);
        assertEquals("4", 4.0, result4, 0.001);
    }

    @Test
    public void testConvertInfixToArr() {
        String arr1 = "(42 + 1) -- 3";
        String[] result1 = pfEval.convertInfixToArr(arr1);
        String[] expected1 = {"(", "42", "+", "1", ")", "-", "~", "3"};
        assertArrayEquals(expected1, result1);

        String arr2 = "(42.35 /-1.86) -- 3";
        String[] result2 = pfEval.convertInfixToArr(arr2);
        String[] expected2 = {"(", "42.35", "/", "~","1.86", ")", "-", "~", "3"};
        assertArrayEquals(expected2, result2);

//        unary + neglected
        String arr3 = "+(x+y)";
        String[] result3 = pfEval.convertInfixToArr(arr3);
        String[] expected3 = {"(", "x", "+", "y", ")"};
        assertArrayEquals(expected3, result3);
    }

    @Test
    public void testConvertInfix_ToPost_regular() {
        String normal1 = "((3+4)*2)/7";
        String[] result1 = pfEval.convertInfixToPost(normal1);
        String[] expected1 =  {"3", "4", "+", "2", "*", "7", "/"};
        assertArrayEquals(expected1, result1);

        String normal2 = "(4+2)+(3*(5-1))";
        String[] result2 = pfEval.convertInfixToPost(normal2);
        String[] expected2 = {"4", "2", "+", "3", "5", "1", "-", "*", "+"};
        assertArrayEquals(expected2, result2);
    }

    @Test
    public void testConvertInfix_ToPost_unary_negation() {
        String normal1 = "-3*---4";
        String[] result1 = pfEval.convertInfixToPost(normal1);
        String[] expected1 = {"3", "~", "4", "~", "~", "~", "*"};
        assertArrayEquals(expected1, result1);

        String normal2 = "7*(4*-3)/(5+1)";
        String[] result2 = pfEval.convertInfixToPost(normal2);
        String[] expected2 = {"7", "4", "3", "~", "*", "*", "5", "1", "+", "/"};
        assertArrayEquals(expected2, result2);

        String normal3 = "-(x+y)";
        String[] result3 = pfEval.convertInfixToPost(normal3);
        String[] expected3 = {"x", "y", "+", "~"};
        assertArrayEquals(expected3, result3);

        String normal4 = "4*-x";
        String[] result4 = pfEval.convertInfixToPost(normal4);
        String[] expected4 = {"4", "x", "~", "*"};
        assertArrayEquals(expected4, result4);

        String normal5 = "2*(-x-y)";
        String[] result5 = pfEval.convertInfixToPost(normal5);
        String[] expected5 = {"2", "x", "~", "y", "-", "*"};
        assertArrayEquals(expected5, result5);

        String normal6 = "(4*x)-y";
        String[] result6 = pfEval.convertInfixToPost(normal6);
        String[] expected6 = {"4", "x", "*", "y", "-"};
        assertArrayEquals(expected6, result6);
    }

    @Test
    public void testConvertInfix_ToPost_emptySpace() {
        String normal1 = "(  ( 3+4   )*2      )/7";
        String[] result1 = pfEval.convertInfixToPost(normal1);
        String[] expected1 =  {"3", "4", "+", "2", "*", "7", "/"};
        assertArrayEquals(expected1, result1);

        String normal2 = "(4  + 2) +(3*(   5- 1))";
        String[] result2 = pfEval.convertInfixToPost(normal2);
        String[] expected2 = {"4", "2", "+", "3", "5", "1", "-", "*", "+"};
        assertArrayEquals(expected2, result2);
    }
}
