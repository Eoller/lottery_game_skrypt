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
public class PlayerDefinition extends VariableDefinition {
    private VariableType variableType;

    public PlayerDefinition(String name, VariableType variableType, Node value){
        super.name = name;
        this.variableType = variableType;
        super.value = value;
    }

    @Override
    public Variable execute() {
        System.out.println("Player def");
        switch (variableType){
            case PLAYER:
                AppContext.addVariable(super.name, value.execute());
                break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
        return null;
    }
}
