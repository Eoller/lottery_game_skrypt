package ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static java.lang.Thread.sleep;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunctionCall extends Instruction {

    private List<Node> args;
    private Identifier identifier;

    @Override
    public Variable execute() {
        switch (identifier.getName()){
            case "print":
                System.out.println(args.get(0).execute());
                break;
            case "wait":
                System.out.println("WAIT FUNct");
                IntegerVariable time = (IntegerVariable) args.get(0).execute();
                try {
                    sleep(time.getValue() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                    throw new RuntimeException("Error. Bad embeded function parameters.");
        }
        return null;
    }
}
