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
public class PlayerVar extends Variable {

    private String playerName;
    private Integer balance;

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
