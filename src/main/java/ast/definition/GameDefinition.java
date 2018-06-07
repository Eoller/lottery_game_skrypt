package ast.definition;

import ast.Node;
import ast.var.GameVariable;
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
        switch (variableType){
            case GAME:
                GameVariable gameVariable = (GameVariable) value.execute();
                AppContext.addVariable(super.name, gameVariable);
                Casino.addGame(gameVariable, super.name );
            break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
        return null;
    }
}
