package ast;

import ast.var.IntegerVar;
import ast.var.PlayerVar;
import ast.var.StringVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInitParams extends Unit {

    private Unit playerName;
    private Unit balance;

    @Override
    public Variable run() {
        Variable plName = playerName.run();
        Variable bal = balance.run();
        try {
            StringVar playerVariable = (StringVar) plName;
            IntegerVar playerBalance = (IntegerVar) bal;
            return new PlayerVar(playerVariable.toString(), playerBalance.getValue());
        }catch (Exception e){
            throw new RuntimeException("Error. Bad argument in Player initialization");
        }
    }
}
