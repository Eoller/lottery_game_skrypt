package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbededFunctionCall extends Node {

    private List<Node> argumentList;
    private String name;

    @Override
    public Variable execute() {
        //if name

        return null;
    }
}
