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
        boolean isTrue = ((BoolVariable) expression.execute()).getValue();
        System.out.println("isTRUE: " + isTrue);
        while (isTrue){
            System.out.println("IN WHILE");
            body.executeProgram();
            isTrue = ((BoolVariable) expression.execute()).getValue();
        }
        return null;
    }
}
