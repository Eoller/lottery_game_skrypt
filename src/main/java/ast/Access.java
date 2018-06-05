package ast;

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
    public void execute() {

    }
}
