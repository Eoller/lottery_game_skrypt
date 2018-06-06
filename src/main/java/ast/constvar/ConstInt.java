package ast.constvar;

import ast.var.IntegerVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 04-Jun-18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConstInt extends ConstValue {

    private int value;

    @Override
    public Variable execute() {
        return new IntegerVariable(value);
    }
}
