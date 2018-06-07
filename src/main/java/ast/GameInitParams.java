package ast;

import ast.var.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameInitParams extends Node {

    private Node bank;
    private Node playerCount;
    private Node status;

    @Override
    public Variable execute() {
        Variable bnk = bank.execute();
        Variable plCount = playerCount.execute();
        Variable stts = status.execute();
        try {
            IntegerVariable gameBank = (IntegerVariable) bnk;
            IntegerVariable gamePlayerCount = (IntegerVariable) plCount;
            IntegerVariable gameStatus = (IntegerVariable) stts;
            return new GameVariable(gamePlayerCount.getValue(), gameBank.getValue(), gameStatus.getValue() );
        }catch (Exception e){
            throw new RuntimeException("Error. Bad argument in Player initialization");
        }
    }
}
