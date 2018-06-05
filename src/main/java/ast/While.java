package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class While extends Instruction {

    private Node expression;
    private Program body;

    @Override
    public Variable execute() {
        System.out.println("While");
        body.executeProgram();
        return null;
    }
}
