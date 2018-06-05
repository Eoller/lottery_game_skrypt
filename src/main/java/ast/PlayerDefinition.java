package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDefinition extends VariableDefinition {
    private VariableType variableType;
    private String playerName;
    private int balance;

    public PlayerDefinition(String name, VariableType variableType, String playerName, int balance){
        super.name = name;
        this.variableType = variableType;
        this.playerName = playerName;
        this.balance = balance;
    }

    @Override
    public Variable execute() {
        System.out.println("Player def");
        return null;
    }
}
