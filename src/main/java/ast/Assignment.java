package ast;

import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment extends Instruction {

    private Node expression;
    private Identifier identifier;

    @Override
    public Variable execute() {
        System.out.println("Assignment");
        Variable result = expression.execute();
        if(AppContext.containsVariable(identifier.getName())){
            AppContext.updateVariable(identifier.getName(), result);
        }else {
            AppContext.addVariable(identifier.getName(), result);
        }
        return null;
    }
}
