package ast.var;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameVariable extends Variable {

    private int playerCount;
    private int bank;
    private int status;

    @Override
    public String toString() {
        return "GameVariable{" +
                "playerCount=" + playerCount +
                ", bank=" + bank +
                ", status=" + status +
                '}';
    }

    @Override
    public VariableType getType() {
        return VariableType.GAME;
    }
}
