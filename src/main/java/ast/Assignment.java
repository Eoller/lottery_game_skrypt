package ast;

import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment extends Instruction {

    private Unit expression;
    private Identifier identifier;

    @Override
    public Variable run() {
        Variable result = expression.run();
        if(AppContext.containsVariable(identifier.getName())){
            AppContext.updateVariable(identifier.getName(), result);
        }else {
            AppContext.addVariable(identifier.getName(), result);
        }
        return null;
    }
}
