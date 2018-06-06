package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerVariable extends Variable {

    private String playerName;
    private int balance;

    @Override
    public String toString() {
        return "PlayerVariable{" +
                "playerName='" + playerName + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }

    @Override
    public VariableType getType() {
        return VariableType.PLAYER;
    }
}
