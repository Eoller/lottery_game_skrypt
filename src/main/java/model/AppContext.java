package model;

import ast.var.GameVariable;
import ast.var.PlayerVariable;
import ast.var.Variable;
import model.gamestub.Casino;
import model.gamestub.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eoller on 05-Jun-18.
 */
public class AppContext {
    public final static Map<String, Variable> variables = new HashMap<>();

    public static boolean addVariable(String name, Variable variable){
        if(variables.containsKey(name)){
            return false;
        }else {
            variables.put(name, variable);
            return true;
        }
    }

    public static void updateVariable(String name, Variable variable){
        variables.replace(name, variable);
    }

    public static Variable getVariable(String name){
        return variables.get(name);
    }

    public static boolean containsVariable(String identifier) {
        return variables.containsKey(identifier);
    }

    public static void printContext(){
        variables.forEach((s, variable) -> System.out.println("[" + s +  "] --> value: (" + variable.toString() + "):" + variable.getType().name()));
    }

    public static void synchronizeContext(){
        AppContext.variables.forEach((s, variable) -> {
            Casino.games.forEach(game -> {
                if(game.getId().equals(s)){
                    GameVariable var = (GameVariable) variable;
                    var.setPlayerCount(game.getPlayers().size());
                    var.setBank(game.getBank());
                    var.setStatus(game.getStatus());
                    if(game.getWinner() != null){
                        var.setWinner(game.getWinner().getId());
                    }
                }
            });
            Casino.players.forEach(player -> {
                if(player.getId().equals(s)){
                    PlayerVariable playerVariable = (PlayerVariable) variable;
                    playerVariable.setBalance(player.getBalance());
                    playerVariable.setPlayerName(player.getName());
                }
            });
        });

    }
}
