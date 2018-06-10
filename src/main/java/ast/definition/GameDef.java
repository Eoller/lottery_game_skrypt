package ast.definition;

import ast.Unit;
import ast.var.GameVar;
import ast.var.Variable;
import ast.var.VariableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
import model.gamestub.Casino;
/**
 * Created by Yahor_Melnik on 03-Jun-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameDef extends VarDef {
    private VariableType variableType;

    public GameDef(String name, VariableType variableType, Unit value){
        super.name = name;
        this.variableType = variableType;
        super.value = value;
    }

    @Override
    public Variable run() {
        switch (variableType){
            case GAME:
                GameVar gameVariable = (GameVar) value.run();
                AppContext.addVariable(super.name, gameVariable);
                Casino.addGame(gameVariable, super.name );
            break;
            default:
                throw new RuntimeException("Error: Expected primitive type");
        }
        return null;
    }
}
