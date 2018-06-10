package ast;

import ast.var.IntegerVar;
import ast.var.PlayerVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.AppContext;
import model.gamestub.Casino;

import java.util.List;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbededFunctionCall extends Unit {

    private List<Unit> argumentList;
    private Identifier identifier;
    private Unit from;


    @Override
    public Variable run() {
        Identifier funName = (Identifier) from;
        String rightSideName = funName.getName();
        Variable leftSideName = identifier.run();
        switch (leftSideName.getType()){
            case GAME:
                switch (rightSideName){
                    case "nextRound":
                        String gameLeftSideId = identifier.getName();
                        Casino.nextRoundOnGameById(gameLeftSideId);
                        AppContext.synchronizeContext();
                        break;
                    case "findWinner":
                        String gameleft = identifier.getName();
                        Casino.findWinnerOnGameById(gameleft);
                        AppContext.synchronizeContext();
                        break;
                }
                break;
            case PLAYER:
                PlayerVar playerVariable = (PlayerVar) leftSideName;
                switch (rightSideName){
                    case "joinGame":
                        String playerId = identifier.getName();
                        Identifier gameId = (Identifier) argumentList.get(0);
                        IntegerVar bet = (IntegerVar) argumentList.get(1).run();
                        Casino.addPlayerToGame(gameId.getName() ,playerId, bet.getValue());

                        AppContext.synchronizeContext();
                        break;
                    case "leaveGame":
                        String playerIdk = identifier.getName();
                        Identifier gameIdk = (Identifier) argumentList.get(0);
                        Casino.kickPlayerFromGame(gameIdk.getName(), playerIdk);

                        AppContext.synchronizeContext();
                        break;
                }
                break;
                default:
        }
        return null;
    }
}
