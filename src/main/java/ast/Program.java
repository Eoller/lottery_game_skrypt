package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eoller on 03-Jun-18.
 */
public class Program {

    List<Instruction> instructionList = new ArrayList<>();

    public void addInstruction(final Instruction instruction){
        instructionList.add(instruction);
    }

    public void executeProgram(){
        instructionList.forEach(instruction -> {
            instruction.execute();
        });
    }

}
