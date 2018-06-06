package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunctionCall extends Instruction {

    private List<Node> args;
    private Identifier identifier;

    @Override
    public Variable execute() {
        return null;
    }
}
