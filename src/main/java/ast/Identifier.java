package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 04-Jun-18.
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Identifier extends Node {

    private String name;

    @Override
    public void execute() {

    }
}
