package ast;

import ast.var.GameVariable;
import ast.var.IntegerVariable;
import ast.var.PlayerVariable;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
import model.gamestub.Casino;
import model.gamestub.Player;

import java.util.List;

import static ast.var.VariableType.GAME;
import static ast.var.VariableType.PLAYER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbededFunctionCall extends Node {

    private List<Node> argumentList;
    private Identifier identifier;
    private Node from;

    /*identifier.from(argumentList)*/

    @Override
    public Variable execute() {
        Identifier funName = (Identifier) from;
        String rightSideName = funName.getName();
        Variable leftSideName = identifier.execute();
        switch (leftSideName.getType()){
            case GAME:
                GameVariable gameVariable = (GameVariable) leftSideName;
                switch (rightSideName){
                    case "nextRound":
                    case "findWinner":

                }
            case PLAYER:
                PlayerVariable playerVariable = (PlayerVariable) leftSideName;
                switch (rightSideName){
                    case "joinGame":
                        String playerId = identifier.getName();
                        Identifier gameId = (Identifier) argumentList.get(0);
                        IntegerVariable bet = (IntegerVariable) argumentList.get(1).execute();
                        Casino.addPlayerToGame(gameId.getName() ,playerId, bet.getValue());
                        PlayerVariable playerAdded = (PlayerVariable) AppContext.getVariable(playerId);
                        playerAdded.setBalance(playerAdded.getBalance() - bet.getValue());
                    case "leaveGame":
                        String playerIdk = identifier.getName();
                        Identifier gameIdk = (Identifier) argumentList.get(0);
                        Casino.kickPlayerFromGame(gameIdk.getName(), playerIdk);
                }
                default:
        }
        return null;
    }
}
