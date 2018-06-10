package ast.constvar;

import ast.var.StringVar;
import ast.var.Variable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@NoArgsConstructor
@Getter
@Setter
public class ConstString extends ConstValue {

    private String value;

    public ConstString(String value){
        this.value = value.substring(1, value.length()-1);
    }

    @Override
    public Variable run() {
        return new StringVar(value);
    }
}
