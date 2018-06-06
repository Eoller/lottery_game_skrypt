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
            throw new RuntimeException("Left side of an expression cannot be null");

        this.left = left;

        this.operator = operator;

        if (right == null)
            this.right = Empty.getInstance();
        else
            this.right = right;
    }


    @Override
    public Variable execute() {
        System.out.println("Expression");
        Variable leftRes = left.execute();
        Variable rightRes = right.execute();

        /*switch (operator){
            case AND:
            case OR:
                return handleLogicalExpression(()) //TODO
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                return handleArithmeticExpression(leftRes, rightRes); //TODO
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
                return handleRelationalExpression(); //TODO
            default:
                throw new RuntimeException("Error. Invalid operator");
        }*/

        return null;
    }
}
