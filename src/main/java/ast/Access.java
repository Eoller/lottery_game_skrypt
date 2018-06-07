package ast;

import ast.constvar.ConstInt;
import ast.constvar.ConstString;
import ast.var.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Access extends Instruction {

    private Node left;
    private Node right;

    @Override
    public Variable execute() {
        Identifier identifier = (Identifier) left;

        if(right instanceof Identifier){
            Identifier rght = (Identifier) right;
            Variable var = left.execute();
            switch (var.getType()){
                case GAME:
                    GameVariable game = (GameVariable) var;
                    switch (rght.getName()){
                        case "bank":
                            return new IntegerVariable(game.getBank());
                        case "playerCount":
                            return new IntegerVariable(game.getPlayerCount());
                        case "status":
                            return new IntegerVariable(game.getStatus());
                    }
                case PLAYER:
                    PlayerVariable player = (PlayerVariable) var;
                    switch (rght.getName()){
                        case "name":
                            return new StringVariable(player.getPlayerName());
                        case "balance":
                            return new IntegerVariable(player.getBalance());
                    }
            }
        }else if(right instanceof EmbededFunctionCall){
            EmbededFunctionCall embededFunctionCall = (EmbededFunctionCall) right;
            embededFunctionCall.execute();
        }
        return null;
    }
}
