package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Eoller on 04-Jun-18.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class If extends Instruction {

    private Node expression;
    private Program body;
    private Else elseBlock;

    @Override
    public Variable execute() {
        //boolean isTrue =
        System.out.println("If");
        return null;
    }
}
