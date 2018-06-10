package ast;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yahor_Melnik on 10-May-18.
 */
@Getter
@Setter
public class Program {

    List<Instruction> instructionList = new ArrayList<>();

    public void addInstruction(final Instruction instruction){
        instructionList.add(instruction);
    }

    public void executeProgram(){
        instructionList.forEach(instruction -> {
            instruction.run();
        });
    }

}
