package ast;

import ast.var.IntegerVar;
import ast.var.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static java.lang.Thread.sleep;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FunctionCall extends Instruction {

    private List<Unit> args;
    private Identifier identifier;

    @Override
    public Variable run() {
        switch (identifier.getName()) {
            case "print":
                System.out.print(args.get(0).run());
                break;
            case "println":
                System.out.println(args.get(0).run());
                break;
            case "wait":
                IntegerVar time = (IntegerVar) args.get(0).run();
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
