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
        return 0;
    }
}
