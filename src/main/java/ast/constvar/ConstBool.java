package ast.constvar;

import ast.var.BoolVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConstBool extends ConstValue {

    private boolean value;

    @Override
    public Variable run() {
        return new BoolVar(value);
    }
}
