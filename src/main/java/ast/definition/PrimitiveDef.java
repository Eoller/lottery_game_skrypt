package ast.definition;

import ast.Unit;
import ast.var.Variable;
import ast.var.VariableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrimitiveDef extends VarDef {

    private VariableType variableType;

    public PrimitiveDef(VariableType variableType, String name, Unit value) {
        this.variableType = variableType;
        super.name = name;
        super.value = value;
    }

    @Override
    public Variable run() {
        switch (variableType){
            case INT:
            case BOOL:
            case STRING:
                AppContext.addVariable(super.name, value.run());
                break;
                default:
                    throw new RuntimeException("Error: Expected primitive type");
        }
        return null;
    }
}
