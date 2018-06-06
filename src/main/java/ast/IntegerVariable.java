package ast;

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
        return 0;
    }

    @Override
    public VariableType getType() {
        return VariableType.INT;
    }
}
