package ast.constvar;

import ast.var.BoolVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 04-Jun-18.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConstBool extends ConstValue {

    private boolean value;

    @Override
    public Variable execute() {
        return new BoolVariable(value);
    }
}
