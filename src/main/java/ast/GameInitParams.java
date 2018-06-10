package ast;

import ast.var.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
public class GameInitParams extends Unit {


    @Override
    public Variable run() {

        try {
            IntegerVar gameBank = new IntegerVar(0);
            IntegerVar gamePlayerCount = new IntegerVar(0);
            IntegerVar gameStatus = new IntegerVar(0);
            return new GameVar(gamePlayerCount.getValue(), gameBank.getValue(), gameStatus.getValue());
        }catch (Exception e){
            throw new RuntimeException("Error. Bad argument in Player initialization");
        }
    }
}
