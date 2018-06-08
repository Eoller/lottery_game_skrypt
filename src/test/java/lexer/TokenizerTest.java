package lexer;

import model.AppContext;
import ast.Program;
import model.Token;
import model.TokenType;
import org.junit.After;
import org.junit.Test;
import parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class TokenizerTest {

    private FileReader fileReader;

    private File file;

    private Tokenizer tokenizer;

    @After
    public void afterTests(){
//        file.delete();
    }

    @Test
    public void shouldGetGameToken(){
        tokenizer = new Tokenizer(createFileWithString("Game game1;"));
        assertEquals(Tokenizer.embededWords.get("Game"), tokenizer.nextToken());
    }


    @Test
    public void shouldGetIdentifierToken(){
        tokenizer = new Tokenizer(createFileWithString("game1;"));
        Token nextToken = tokenizer.nextToken();
        assertEquals(TokenType.IDENTIFIER, nextToken.getType());
        assertEquals("game1", nextToken.getValue());
    }

    @Test
    public void randomTest() throws Exception {
        /*tokenizer = new Tokenizer(createFileWithString("if(true){" +
                "int a = 5 + 10" +
                "Game a(12,15,20)" +
                "Player b(" + '"' + "Hello" + '"' + ", 500)" +
                "a.joinGame()" +
                "}" +
                "while(true){" +
                "bool isTrue = false" +
                "}" +
                "$"));*/
        tokenizer = new Tokenizer(new File("Example.txt"));
        /*tokenizer = new Tokenizer(createFileWithString("int a = 100\n" +
                "a = (a + 50/2)*3\n" +
                "String  b = \"Alek\"\n" +
                "println(a)\n" +
                "println(b)\n" +
                "\n" +
                "while(a < 500){\n" +
                "\ta = a + 25\n" +
                "\tprint(\"a = \")\n" +
                "\tprintln(a)\n" +
                "}\n" +
                "\n" +
                "if(b == \"Alek\"){\n" +
                "\tprintln(\"TAK\")\n" +
                "}\n" +
                "\n" +
                "int i = 0\n" +
                "while(i < 10){\n" +
                "\tif(i == 10){\n" +
                "\t\tprintln(\"IN WHILE\")\n" +
                "\t}\n" +
                "\ti = i + 2\n" +
                "\tprintln(i)\n" +
                "}\n" +
                "\n" +
                "\n" +
                "Player player1(b, a)\n" +
                "Game game1(0,0,0)\n" +
                "\n" +
                "print(\"game1 bank = \")\n" +
                "println(game1.bank)\n" +
                "\n" +
                "\n" +
                "print(\"player1 balance = \")\n" +
                "println(player1.balance)\n" +
                "\n" +
                "print(\"player1 name = \")\n" +
                "println(player1.name)\n" +
                "\n" +
                "$"));*/


        Parser parser = new Parser(tokenizer);
        Program parse = parser.parse();
        parse.executeProgram();
        AppContext.printContext();
    }

    private File createFileWithString(String data) {
        try {
            file = new File("Example.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {}
        return file;
    }

    @Test
    public void shouldGetCharSymbols(){
        tokenizer = new Tokenizer(createFileWithString(".,;"));
        Token nextToken = tokenizer.nextToken();
        Token nextToken1 = tokenizer.nextToken();
        Token nextToken2 = tokenizer.nextToken();
        assertEquals(Tokenizer.embededWords.get("."), nextToken);
        assertEquals(Tokenizer.embededWords.get(","), nextToken1 );
        assertEquals(Tokenizer.embededWords.get(";"), nextToken2);
    }

    @Test
    public void shouldGetStringSymbols(){
        tokenizer = new Tokenizer(createFileWithString("somestring"));
        assertEquals(new Token(TokenType.CONST_STRING, "somestring").getValue(), tokenizer.nextToken().getValue());
    }

    @Test
    public void shouldGetArithmeticsOperator(){
        tokenizer = new Tokenizer(createFileWithString("+-*/"));
        Token nextToken = tokenizer.nextToken();
        Token nextToken1 = tokenizer.nextToken();
        Token nextToken2 = tokenizer.nextToken();
        Token nextToken3 = tokenizer.nextToken();
        assertEquals(Tokenizer.embededWords.get("+"), nextToken);
        assertEquals(Tokenizer.embededWords.get("-"), nextToken1);
        assertEquals(Tokenizer.embededWords.get("*"), nextToken2);
        assertEquals(Tokenizer.embededWords.get("/"), nextToken3);
    }


    //[QUESTION] ETO DOLZNY BYC RAZNYJE OBJEKTY TOKENOW, ILI ODNI I TE ZE
}