package ast;

import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmbededVar extends Node {

    private String name;

    @Override
    public Variable execute() {
        //return just name
        return null;
    }
}
