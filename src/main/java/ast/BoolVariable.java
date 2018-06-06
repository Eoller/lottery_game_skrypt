package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoolVariable extends Variable implements Comparable{

    private Boolean value;

    public BoolVariable(boolean value){
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
        if (object instanceof BoolVariable) {
            BoolVariable bool = (BoolVariable) object;
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
