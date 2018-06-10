package model;


import ast.var.BoolVar;
import ast.Comparable;
import ast.var.Variable;
import ast.var.VariableType;
import ast.var.IntegerVar;
import ast.var.StringVar;
/**
 * Created by Yahor_Melnik on 03-Jun-18.
 */
public abstract class VarOperations {
    public static BoolVar less(Comparable a, Comparable b) {
        return new BoolVar(a.compare(b) == -1);
    }

    public static BoolVar lessEqual(Comparable a, Comparable b) {
        int result = a.compare(b);
        return new BoolVar(result == 0 || result == -1);
    }

    public static BoolVar equal(Comparable a, Comparable b) {
        return new BoolVar(a.compare(b) == 0);
    }

    public static BoolVar notEqual(Comparable a, Comparable b) {
        return new BoolVar(a.compare(b) != 0);
    }

    public static BoolVar greater(Comparable a, Comparable b) {
        return new BoolVar(a.compare(b) == 1);
    }

    public static BoolVar greaterEqual(Comparable a, Comparable b) {
        int result = a.compare(b);
        return new BoolVar(result == 1 || result == 0);
    }

    public static BoolVar and(BoolVar a, BoolVar b) {
        return new BoolVar(a.getValue() && b.getValue());
    }

    public static BoolVar or(BoolVar a, BoolVar b) {
        return new BoolVar(a.getValue() || b.getValue());
    }

    private static IntegerVar add(IntegerVar a, IntegerVar b) {
        return new IntegerVar(a.getValue() + b.getValue());
    }

    private static StringVar add(StringVar a, Variable b) {
        if (b instanceof IntegerVar)
            return new StringVar(a.getValue().append(String.valueOf(((IntegerVar) b).getValue())).toString());
        else if (b instanceof BoolVar)
            return new StringVar(a.getValue().append(String.valueOf(((BoolVar) b).getValue())).toString());
        else
            return new StringVar(a.getValue().append(((StringVar) b).getValue()).toString());
    }

    public static Variable add(Variable a, Variable b){
        if (a.getType() == VariableType.STRING)
            return add((StringVar) a, b);
        else
            return add((IntegerVar) a, (IntegerVar) b);
    }

    public static IntegerVar subtract(IntegerVar a, IntegerVar b) {
        return new IntegerVar(a.getValue() - b.getValue());
    }

    public static IntegerVar multiply(IntegerVar a, IntegerVar b) {
        return new IntegerVar(a.getValue() * b.getValue());
    }

    public static IntegerVar divide(IntegerVar a, IntegerVar b) {
        if (b.getValue() == 0)
            throw new ArithmeticException("Error. Division by 0");

        return new IntegerVar(a.getValue() / b.getValue());
    }

    public static void println(Variable variable) {
        System.out.println(variable.toString());
    }
}
