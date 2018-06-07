package model.gamestub;

import ast.var.GameVariable;
import ast.var.PlayerVariable;

import java.util.ArrayList;
import java.util.List;

public abstract class Casino {

    private static final List<Game> games = new ArrayList<>();
    private static final List<Player> players = new ArrayList<>();

    public static void addPlayer(PlayerVariable playerVariable, String id){
        players.add(new Player(id, playerVariable.getPlayerName(), playerVariable.getBalance()));
        System.out.println("Player " + id + " joined our Casino.");
    }

    public static void addGame(GameVariable gameVariable, String id){
        games.add(new Game(id));
        System.out.println("Game " + id + " was created.");
    }

    public static void addPlayerToGame(String gameId, String playerId, int bet){
        games.get(findGameIndexByGameId(gameId))
                .addPlayer(players.get(findPlayerIndexByPlayerId(playerId)), bet);
        System.out.println("Player " + playerId + " just joined game(" + gameId + ") with bet = " + bet);
    }

    public static void kickPlayerFromGame(String gameId, String playerId){
        games.get(findGameIndexByGameId(gameId)).kickPlayer(playerId);
        System.out.println("Player " + playerId + " just leaved game(" + gameId + ")");
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
