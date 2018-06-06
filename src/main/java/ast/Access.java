package ast;

import ast.var.Variable;
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
        Variable from = left.execute();


        //if right -> embededVar do find left in app scope, then get right name
        //if right -> embededFun do execute
        System.out.println("Access");
        return null;
    }
}
