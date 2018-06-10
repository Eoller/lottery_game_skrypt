package ast.constvar;

import ast.var.IntegerVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConstInt extends ConstValue {

    private int value;

    @Override
    public Variable run() {
        return new IntegerVar(value);
    }
}
