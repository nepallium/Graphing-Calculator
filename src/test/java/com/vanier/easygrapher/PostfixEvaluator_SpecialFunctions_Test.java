package com.vanier.easygrapher;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PostfixEvaluator_SpecialFunctions_Test {

    private PostfixEvaluator pfEval;

    @Before
    public void setUp() {
        pfEval = new PostfixEvaluator();
    }

    @Test
    public void testExponentiation() {
        String expr1 = "4^5";
        String[] postfix1 = pfEval.convertInfixToPost(expr1);
        double result1 = pfEval.evaluatePostfix(postfix1);
        assertEquals(expr1, 1024.0, result1, 0.001);

        String expr2 = "5^(5-3)";
        String[] postfix2 = pfEval.convertInfixToPost(expr2);
        double result2 = pfEval.evaluatePostfix(postfix2);
        assertEquals(expr2, 25.0, result2, 0.001);

        String expr3 = "5^(2-4)";
        String[] postfix3 = pfEval.convertInfixToPost(expr3);
        double result3 = pfEval.evaluatePostfix(postfix3);
        assertEquals(expr3, 0.04, result3, 0.001);

        String expr4 = "7*(5+2)/5^(5-3)";
        String[] postfix4 = pfEval.convertInfixToPost(expr4);
        double result4 = pfEval.evaluatePostfix(postfix4);
        assertEquals(expr4, 1.96, result4, 0.001);
    }
}
