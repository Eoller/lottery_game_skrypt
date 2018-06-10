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
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Identifier extends Unit {

    private String name;

    @Override
    public Variable run() {
        return AppContext.getVariable(name);
    }
}
