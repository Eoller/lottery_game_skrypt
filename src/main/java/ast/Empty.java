package ast;


public class Empty extends Instruction {
    private static Empty instance;

    public static Empty getInstance() {
        if (instance == null)
            instance = new Empty();

        return instance;
    }

    @Override
    public void execute() {

    }
}
