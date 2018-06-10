package parser;

import ast.Program;
import ast.var.*;
import lexer.Tokenizer;
import model.AppContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;
/**
 * Created by Eoller on 03-Jun-18.
 */
public class ParserTest {

    private FileReader fileReader;

    private File file;

    private Tokenizer tokenizer;

    @Before
    public void clear(){
        AppContext.variables.clear();
    }

    @Test
    public void shouldParseIntInit(){
        String forTest = "int a = 5\n" +
                "int b = 5 + 3\n" +
                "int c = a + b\n" +
                "int d = c * 2\n" +
                "int e = d / 3\n" +
                "int f = (e - d)*(a + b)\n" +
                "$";
        Parser parser = new Parser(new Tokenizer(createFileWithString(forTest)));
        try {
            Program parsed = parser.parse();
            parsed.executeProgram();
            assertNotNull(AppContext.getVariable("a"));
            assertNotNull(AppContext.getVariable("c"));
            assertNotNull(AppContext.getVariable("b"));
            assertNotNull(AppContext.getVariable("d"));
            assertNotNull(AppContext.getVariable("e"));
            assertNotNull(AppContext.getVariable("f"));
            IntegerVar a = (IntegerVar) AppContext.getVariable("a");
            IntegerVar b = (IntegerVar) AppContext.getVariable("b");
            IntegerVar c = (IntegerVar) AppContext.getVariable("c");
            IntegerVar d = (IntegerVar) AppContext.getVariable("d");
            IntegerVar e = (IntegerVar) AppContext.getVariable("e");
            IntegerVar f = (IntegerVar) AppContext.getVariable("f");
            assertEquals(5, a.getValue().intValue());
            assertEquals(8, b.getValue().intValue());
            assertEquals(13, c.getValue().intValue());
            assertEquals(26, d.getValue().intValue());
            assertEquals(8, e.getValue().intValue());
            assertEquals(-234, f.getValue().intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldParseStringInit(){
        String forTest = "\n" +
                "String a = \"Hello\"\n" +
                "String b = \"Hello\" + \" world\"\n" +
                "\n" +
                "$";
        Parser parser = new Parser(new Tokenizer(createFileWithString(forTest)));
        try {
            Program parsed = parser.parse();
            parsed.executeProgram();
            assertNotNull(AppContext.getVariable("a"));
            assertNotNull(AppContext.getVariable("b"));
            StringVar a = (StringVar) AppContext.getVariable("a");
            StringVar b = (StringVar) AppContext.getVariable("b");

            assertEquals("Hello", a.getValue().toString());
            assertEquals("Hello world", b.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldParseBoolInit(){
        String forTest = "bool a = true\n" +
                "bool b = false\n" +
                "bool c = true || false\n" +
                "bool d = true && false\n" +
                "bool e = 5 > 1\n" +
                "\n" +
                "$";
        Parser parser = new Parser(new Tokenizer(createFileWithString(forTest)));
        try {
            Program parsed = parser.parse();
            parsed.executeProgram();
            assertNotNull(AppContext.getVariable("a"));
            assertNotNull(AppContext.getVariable("b"));
            assertNotNull(AppContext.getVariable("c"));
            assertNotNull(AppContext.getVariable("d"));
            assertNotNull(AppContext.getVariable("e"));
            BoolVar a = (BoolVar) AppContext.getVariable("a");
            BoolVar b = (BoolVar) AppContext.getVariable("b");
            BoolVar c = (BoolVar) AppContext.getVariable("c");
            BoolVar d = (BoolVar) AppContext.getVariable("d");
            BoolVar e = (BoolVar) AppContext.getVariable("e");

            assertEquals("true", a.getValue().toString());
            assertEquals("false", b.getValue().toString());
            assertEquals("true", c.getValue().toString());
            assertEquals("false", d.getValue().toString());
            assertEquals("true", e.getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldParseGameInit(){
        AppContext.variables.clear();
        String forTest = "Game a()\n" +
                "\n" +
                "$";
        Parser parser = new Parser(new Tokenizer(createFileWithString(forTest)));
        try {
            Program parsed = parser.parse();
            parsed.executeProgram();
            assertNotNull(AppContext.getVariable("a"));
            GameVar a = (GameVar) AppContext.getVariable("a");

            assertEquals("", a.getWinner().toString());
            assertEquals(0, a.getBank().intValue());
            assertEquals(0, a.getStatus().intValue());
            assertEquals(0, a.getPlayerCount().intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldParsePlayerInit(){
        AppContext.variables.clear();
        String forTest = "Player a(\"Alek\", 1000)\n" +
                "\n" +
                "$";
        Parser parser = new Parser(new Tokenizer(createFileWithString(forTest)));
        try {
            Program parsed = parser.parse();
            parsed.executeProgram();
            assertNotNull(AppContext.getVariable("a"));
            PlayerVar a = (PlayerVar) AppContext.getVariable("a");

            assertEquals("Alek", a.getPlayerName().toString());
            assertEquals(1000, a.getBalance().intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createFileWithString(String data) {
        try {
            file = new File("Temp.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {}
        return file;
    }
}
