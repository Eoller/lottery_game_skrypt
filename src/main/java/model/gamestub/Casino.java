package model.gamestub;

import ast.var.GameVar;
import ast.var.PlayerVar;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Yahor_Melnik on 03-Jun-18.
 */
@Getter
@Setter
public abstract class Casino {

    public static final List<Game> games = new ArrayList<>();
    public static final List<Player> players = new ArrayList<>();

    public static void addPlayer(PlayerVar playerVariable, String id){
        players.add(new Player(id, playerVariable.getPlayerName(), playerVariable.getBalance()));
        System.out.println("[CASINO LOG] Player " + id + " joined our Casino.");
    }

    public static void addGame(GameVar gameVariable, String id){
        games.add(new Game(id));
        System.out.println("[CASINO LOG] Game " + id + " was created.");
    }

    public static void addPlayerToGame(String gameId, String playerId, int bet){
        games.get(findGameIndexByGameId(gameId))
                .addPlayer(players.get(findPlayerIndexByPlayerId(playerId)), bet);
        System.out.println("[CASINO LOG] Player " + playerId + " just joined game(" + gameId + ") with bet = " + bet);
    }

    public static void kickPlayerFromGame(String gameId, String playerId){
        games.get(findGameIndexByGameId(gameId)).kickPlayer(playerId);
        System.out.println("[CASINO LOG] Player " + playerId + " just leaved game(" + gameId + ")");
    }

    public static void nextRoundOnGameById(String gameId){
        games.get(findGameIndexByGameId(gameId)).nextRound();
    }

    public static void findWinnerOnGameById(String gameId){
        games.get(findGameIndexByGameId(gameId)).findWinner();
    }

    private static int findGameIndexByGameId(String gameId){
        for (int i = 0; i< games.size(); i++){
            if(games.get(i).getId().equals(gameId))
                return i;
        }
        return -1;
    }

    private static int findPlayerIndexByPlayerId(String playerId){
        for(int i = 0; i< players.size(); i++){
            if(players.get(i).getId().equals(playerId))
                return i;
        }
        return -1;
    }

}
