package ast.var;

import ast.Comparable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoolVar extends Variable implements Comparable {

    private Boolean value;

    public BoolVar(boolean value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public VariableType getType() {
        return VariableType.BOOL;
    }

    @Override
    public int compare(Object object) {
        if (object instanceof BoolVar) {
            BoolVar bool = (BoolVar) object;
            if (value && value != bool.getValue())
                return -1;
            else if (!value && value != bool.getValue())
                return 1;
            else
                return 0;
        } else
            throw new RuntimeException("Error. Comparing BoolVariable with a non-BoolVariable type");
    }
}
