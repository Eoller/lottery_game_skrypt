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

    private Integer playerCount;
    private Integer bank;
    private Integer status;
    private String winner = "";


    public GameVariable(Integer playerCount, Integer bank, Integer status) {
        this.playerCount = playerCount;
        this.bank = bank;
        this.status = status;
    }

    @Override
    public String toString() {
        return "GameVariable{" +
                "playerCount=" + playerCount +
                ", bank=" + bank +
                ", status=" + status +
                ", winner='" + winner + '\'' +
                '}';
    }

    @Override
    public VariableType getType() {
        return VariableType.GAME;
    }
}
