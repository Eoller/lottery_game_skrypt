package intepreter;

import lexer.Tokenizer;
import parser.Parser;

import java.io.File;

/**
 * Created by Eoller on 09-Jun-18.
 */
public class Intepreter {

    public void run(File file) throws Exception {
        Tokenizer tokenizer = new Tokenizer(file);
        Parser parser = new Parser(tokenizer);
        parser.parse().executeProgram();
    }

    public void run() throws Exception {
        Tokenizer tokenizer = new Tokenizer(new File("Example.txt"));
        Parser parser = new Parser(tokenizer);
        parser.parse().executeProgram();
    }

    public static void main(String[] args) {
        Intepreter intepreter = new Intepreter();
        try {
            intepreter.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
