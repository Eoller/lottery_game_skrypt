package ast;


import lombok.Getter;
import model.TokenType;

// expression = T {logicalOperator T}
// T = T2 {relationalOperator T2}
// T2 = T3 {lowPriorityArithmeticOperator T3}
// T3 = operand {highPriorityArithmeticOperator operand}
// operand = identifier | constValue | functionCall | '(' expression ')'
@Getter
public class Expression extends Node {
    private Node left;
    private TokenType operator;
    private Node right;

    public Expression(Node left, TokenType operator, Node right) {
        if (left == null)
            throw new RuntimeException("Left side of an expression must not be null");

        this.left = left;

        this.operator = operator;

        if (right == null)
            this.right = Empty.getInstance();
        else
            this.right = right;
    }


    @Override
    public void execute() {

    }
}
