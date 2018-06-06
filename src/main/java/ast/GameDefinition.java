package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameDefinition extends VariableDefinition {
    private VariableType variableType;

    public GameDefinition(String name, VariableType variableType, Node value){
        super.name = name;
        this.variableType = variableType;
        super.value = value;
    }

    @Override
    public Variable execute() {
        System.out.println("Game Definition");
        switch (variableType){
            case GAME:
                AppContext.addVariable(super.name, value.execute());
            break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
        return null;
    }
}
