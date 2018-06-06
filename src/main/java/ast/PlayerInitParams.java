package ast;

import ast.var.PlayerVariable;
import ast.var.StringVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInitParams extends Node {

    private Node playerName;
    private int balance;

    @Override
    public Variable execute() {
        Variable variable = playerName.execute();
        switch (variable.getType()){
            case STRING:
                StringVariable playerVariable = (StringVariable) variable;
                return new PlayerVariable(playerVariable.toString(), balance);
                default:
                    throw new RuntimeException("Error. Bad argument in Player initialization");
        }

    }
}
