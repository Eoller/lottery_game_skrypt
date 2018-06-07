package ast.var;

import ast.Comparable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegerVariable extends Variable implements Comparable {

    private Integer value;

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compare(Object object) {
        if (object instanceof IntegerVariable) {
            IntegerVariable integerVariable = (IntegerVariable) object;

            if (value > integerVariable.getValue())
                return 1;
            else if (value < integerVariable.getValue())
                return -1;
            else
                return 0;
        } else
            throw new RuntimeException("Error. Comparing IntegerVariable with a non-IntegerVariable type");
    }

    @Override
    public VariableType getType() {
        return VariableType.INT;
    }
}
