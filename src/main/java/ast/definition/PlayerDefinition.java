package ast.definition;

import ast.Node;
import ast.var.PlayerVariable;
import ast.var.Variable;
import ast.var.VariableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
import model.gamestub.Casino;

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
        switch (variableType){
            case PLAYER:
                PlayerVariable playerVariable = (PlayerVariable)  value.execute();
                AppContext.addVariable(super.name,playerVariable);
                Casino.addPlayer(playerVariable, super.name);
                break;
            default:
                throw new RuntimeException("Error: Expected non primitive type");
        }
        return null;
    }
}
