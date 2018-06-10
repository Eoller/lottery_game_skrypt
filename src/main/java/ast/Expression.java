package ast;


import ast.var.BoolVar;
import ast.var.IntegerVar;
import ast.var.Variable;
import lombok.Getter;
import model.VarOperations;
import model.TokenType;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
public class Expression extends Unit {
    private Unit left;
    private TokenType operator;
    private Unit right;

    public Expression(Unit left, TokenType operator, Unit right) {
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
    public Variable run() {
        Variable leftRes = left.run();
        Variable rightRes = right.run();

        switch (operator){
            case AND:
            case OR:
                return handleLogicalExpression((BoolVar) leftRes, (BoolVar) rightRes);
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
                return VarOperations.add(leftResult, rightResult);
            case MINUS:
                return VarOperations.subtract((IntegerVar)leftResult, (IntegerVar) rightResult);
            case MULTIPLY:
                return VarOperations.multiply((IntegerVar)leftResult, (IntegerVar)rightResult);
            case DIVIDE:
                return VarOperations.divide((IntegerVar)leftResult, (IntegerVar)rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleLogicalExpression(BoolVar leftResult, BoolVar rightResult) {
        switch (operator) {
            case AND:
                return VarOperations.and(leftResult, rightResult);
            case OR:
                return VarOperations.or(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }

    private Variable handleRelationalExpression(Comparable leftResult, Comparable rightResult) {
        switch (operator) {
            case LESS:
                return VarOperations.less(leftResult, rightResult);
            case LESS_EQUAL:
                return VarOperations.lessEqual(leftResult, rightResult);
            case EQUAL:
                return VarOperations.equal(leftResult, rightResult);
            case NOT_EQUAL:
                return VarOperations.notEqual(leftResult, rightResult);
            case GREATER:
                return VarOperations.greater(leftResult, rightResult);
            case GREATER_EQUAL:
                return VarOperations.greaterEqual(leftResult, rightResult);
            default:
                throw new RuntimeException("Unexpected operator in expression " + operator);
        }
    }
}
