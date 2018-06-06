package ast;

import ast.var.BoolVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Eoller on 04-Jun-18.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class If extends Instruction {

    private Node expression;
    private Program body;
    private Else elseBlock;

    @Override
    public Variable execute() {
        boolean isTrueEx = ((BoolVariable) expression.execute()).getValue();
        if(isTrueEx){
            body.executeProgram();
        }else if(elseBlock!=null){
            elseBlock.getBody().executeProgram();
        }
        System.out.println("If");
        return null;
    }
}
