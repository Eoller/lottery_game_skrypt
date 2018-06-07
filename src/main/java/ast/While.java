package ast;

import ast.var.BoolVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class While extends Instruction {

    private Node expression;
    private Program body;

    @Override
    public Variable execute() {
        boolean isTrue = ((BoolVariable) expression.execute()).getValue();
        while (isTrue){
            body.executeProgram();
            isTrue = ((BoolVariable) expression.execute()).getValue();
        }
        return null;
    }
}
