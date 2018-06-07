package ast.constvar;

import ast.var.StringVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 04-Jun-18.
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
    public Variable execute() {
        return new StringVariable(value);
    }
}
