package ast;

import ast.var.IntegerVariable;
import ast.var.PlayerVariable;
import ast.var.StringVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ast.var.VariableType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInitParams extends Node {

    private Node playerName;
    private Node balance;

    @Override
    public Variable execute() {
        Variable plName = playerName.execute();
        Variable bal = balance.execute();
        try {
            StringVariable playerVariable = (StringVariable) plName;
            IntegerVariable playerBalance = (IntegerVariable) bal;
            return new PlayerVariable(playerVariable.toString(), playerBalance.getValue());
        }catch (Exception e){
            throw new RuntimeException("Error. Bad argument in Player initialization");
        }
    }
}
