package ast;

import ast.var.BoolVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class If extends Instruction {

    private Unit expression;
    private Program body;
    private Else elseBlock;

    @Override
    public Variable run() {
        boolean isTrueEx = ((BoolVar) expression.run()).getValue();
        if(isTrueEx){
            body.executeProgram();
        }else if(elseBlock!=null){
            elseBlock.getBody().executeProgram();
        }
        return null;
    }
}
