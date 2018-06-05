package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameDefinition extends VariableDefinition {
    private VariableType variableType;
    private int bank;
    private int status;
    private int player_count;

    public GameDefinition(String name, VariableType variableType, int bank, int status, int player_count){
        super.name = name;
        this.variableType = variableType;
        this.bank = bank;
        this.status = status;
        this.player_count = player_count;
    }

    @Override
    public Variable execute() {
        System.out.println("Game Definition");
        return null;
    }
}
