package ast;

import ast.var.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameInitParams extends Node {


    @Override
    public Variable execute() {

        try {
            IntegerVariable gameBank = new IntegerVariable(0);
            IntegerVariable gamePlayerCount = new IntegerVariable(0);
            IntegerVariable gameStatus = new IntegerVariable(0);
            return new GameVariable(gamePlayerCount.getValue(), gameBank.getValue(), gameStatus.getValue());
        }catch (Exception e){
            throw new RuntimeException("Error. Bad argument in Player initialization");
        }
    }
}
