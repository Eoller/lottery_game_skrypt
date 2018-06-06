package ast;


import ast.var.BoolVariable;
import ast.var.IntegerVariable;
import ast.var.Variable;
import lombok.Getter;
import model.CommonOperations;
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

        switch (operator){
            case AND:
            case OR:
                return handleLogicalExpression((BoolVariable) leftRes, (BoolVariable) rightRes);
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                return handleArithmeticExpression(leftRes, rightRes);
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
            case EQUAL:
            case NOT_EQUAL:
                return handleRelationalExpression((Comparable) leftRes, (Comparable) rightRes);
            default:
                throw new RuntimeException("Error. Invalid operator");
        }
    }

    private Variable handleArithmeticExpression(Variable leftResult, Variable rightResult) {
        switch (operator) {
            case PLUS:
                return CommonOperations.add(leftResult, rightResult);
            case MINUS:
                return CommonOperations.subtract((IntegerVariable)leftResult, (IntegerVariable) rightResult);
            case MULTIPLY:
                return CommonOperations.multiply((IntegerVariable)leftResult, (IntegerVariable)rightResult);
            case DIVIDE:
                return CommonOperations.divide((IntegerVariable)leftResult, (IntegerVariable)rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleLogicalExpression(BoolVariable leftResult, BoolVariable rightResult) {
        switch (operator) {
            case AND:
                return CommonOperations.and(leftResult, rightResult);
            case OR:
                return CommonOperations.or(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleRelationalExpression(Comparable leftResult, Comparable rightResult) {
        switch (operator) {
            case LESS:
                return CommonOperations.less(leftResult, rightResult);
            case LESS_EQUAL:
                return CommonOperations.lessEqual(leftResult, rightResult);
            case EQUAL:
                return CommonOperations.equal(leftResult, rightResult);
            case NOT_EQUAL:
                return CommonOperations.notEqual(leftResult, rightResult);
            case GREATER:
                return CommonOperations.greater(leftResult, rightResult);
            case GREATER_EQUAL:
                return CommonOperations.greaterEqual(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }
}
