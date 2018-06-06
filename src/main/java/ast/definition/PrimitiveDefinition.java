package ast.definition;

import ast.Node;
import ast.var.Variable;
import ast.var.VariableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;

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
        switch (variableType){
            case INT:
            case BOOL:
            case STRING:
                AppContext.addVariable(super.name, value.execute());
                break;
                default:
                    throw new RuntimeException("Error: Expected primitive type");
        }
        System.out.println("Primitive definition of ->" + name);
        return null;
    }
}
