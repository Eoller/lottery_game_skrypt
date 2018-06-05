package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 05-Jun-18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment extends Instruction {

    private Node expression;
    private Identifier identifier;

    @Override
    public Variable execute() {
        System.out.println("Assignment");
        return null;
    }
}
