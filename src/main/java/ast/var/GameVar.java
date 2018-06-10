package ast.var;

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
public class GameVar extends Variable {

    private Integer playerCount;
    private Integer bank;
    private Integer status;
    private String winner = "";


    public GameVar(Integer playerCount, Integer bank, Integer status) {
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
