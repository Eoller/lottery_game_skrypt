package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInitParams extends Node {

    private String playerName;
    private int balance;

    @Override
    public Variable execute() {
        return new PlayerVariable(playerName, balance);
    }
}
