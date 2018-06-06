package ast;

import ast.var.GameVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameInitParams extends Node {

    private int bank;
    private int playerCount;
    private int status;

    @Override
    public Variable execute() {
        return new GameVariable(playerCount, bank, status);
    }
}
