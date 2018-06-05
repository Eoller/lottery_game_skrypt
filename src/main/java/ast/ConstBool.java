package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Eoller on 04-Jun-18.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConstBool extends ConstValue {

    private boolean value;

    @Override
    public void execute() {

    }
}
