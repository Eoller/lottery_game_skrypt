package ast;

import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;

/**
 * Created by Eoller on 04-Jun-18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Identifier extends Node {

    private String name;

    @Override
    public Variable execute() {
        return AppContext.getVariable(name);
    }
}
