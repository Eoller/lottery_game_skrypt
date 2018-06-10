package ast.definition;

import ast.Unit;
import ast.var.PlayerVar;
import ast.var.Variable;
import ast.var.VariableType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
import model.gamestub.Casino;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDef extends VarDef {
    private VariableType variableType;

    public PlayerDef(String name, VariableType variableType, Unit value){
        super.name = name;
        this.variableType = variableType;
        super.value = value;
    }

    @Override
    public Variable run() {
        switch (variableType){
            case PLAYER:
                PlayerVar playerVariable = (PlayerVar)  value.run();
                AppContext.addVariable(super.name,playerVariable);
                Casino.addPlayer(playerVariable, super.name);
                break;
            default:
                throw new RuntimeException("Error: Expected non primitive type");
        }
        return null;
    }
}
