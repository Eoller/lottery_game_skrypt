package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrimitiveDefinition extends VariableDefinition {

    private VariableType variableType;

    public PrimitiveDefinition(VariableType variableType, String name, Node value) {
        this.variableType = variableType;
        super.name = name;
        super.value = value;
    }

    @Override
    public Variable execute() {
        System.out.println("Primitive definition of ->" + name);
        return null;
    }
}
