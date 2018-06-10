package ast;

import ast.var.BoolVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class While extends Instruction {

    private Unit expression;
    private Program body;

    @Override
    public Variable run() {
        boolean isTrue = ((BoolVar) expression.run()).getValue();
        while (isTrue){
            body.executeProgram();
            isTrue = ((BoolVar) expression.run()).getValue();
        }
        return null;
    }
}
