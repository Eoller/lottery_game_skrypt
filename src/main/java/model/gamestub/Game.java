package model.gamestub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    private String id;
    private List<Player> players = new ArrayList<>();
    private int bank;
    private int status; //1 -started, 2-finished

    public Game(String id){
        this.id = id;
    }

    public void addPlayer(Player player, int bet){
        player.setBalance(player.getBalance()-bet);
        bank = bank + bet;
        players.add(player);
    }

    public void nextRound(){
        if(status != 1)
            status = 1;
    }

    public int findPlayerIndexByPlayerId(String playerId){
        for(int i=0 ; i < players.size(); i++){
            if(players.get(i).getId().equals(playerId)){
                return i;
            }
        }
        return -1;
    }

    public void kickPlayer(String playerId){
        for(int i = 0; i < players.size(); i++){
            String id = players.get(i).getId();
            if(id.equals(playerId)){
                players.remove(id);
            }
        }
    }

}
