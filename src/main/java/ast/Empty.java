package ast;


import ast.var.Variable;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
public class Empty extends Instruction {
    private static Empty instance;

    public static Empty getInstance() {
        if (instance == null)
            instance = new Empty();

        return instance;
    }

    @Override
    public Variable run() {
        return null;
    }
}
