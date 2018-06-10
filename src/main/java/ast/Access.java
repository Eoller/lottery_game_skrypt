package ast;

import ast.var.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Access extends Instruction {

    private Unit left;
    private Unit right;

    @Override
    public Variable run() {
        Identifier identifier = (Identifier) left;

        if (right instanceof Identifier) {
            Identifier rght = (Identifier) right;
            Variable var = left.run();
            switch (var.getType()) {
                case GAME:
                    GameVar game = (GameVar) var;
                    switch (rght.getName()) {
                        case "bank":
                            return new IntegerVar(game.getBank());
                        case "playerCount":
                            return new IntegerVar(game.getPlayerCount());
                        case "status":
                            return new IntegerVar(game.getStatus());
                        case "winner":
                            return new StringVar(game.getWinner());
                    }
                case PLAYER:
                    PlayerVar player = (PlayerVar) var;
                    switch (rght.getName()) {
                        case "name":
                            return new StringVar(player.getPlayerName());
                        case "balance":
                            return new IntegerVar(player.getBalance());
                    }
            }
        } else if (right instanceof EmbededFunctionCall) {
            EmbededFunctionCall embededFunctionCall = (EmbededFunctionCall) right;
            embededFunctionCall.run();
        }
        return null;
    }
}
