package Model;

import lombok.Getter;

@Getter
public class Function {

    private String exprStr;
    private Token[] postfix;
    private String derivativeStr;
    private Token[] derivativePostfix;

    private static final PostfixEvaluator evaluator = new PostfixEvaluator();

    public Function(String exprStr) {
        this.exprStr = exprStr;
        this.postfix =  evaluator.convertInfixToPost(exprStr);
    }

    public double valueAt(double x) {
        return evaluator.evaluatePostfix(this.postfix, x);
    }
}
