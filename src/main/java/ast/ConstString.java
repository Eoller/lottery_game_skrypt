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
@Getter
@Setter
public class ConstString extends ConstValue {

    private String value;

    @Override
    public void execute() {

    }
}
