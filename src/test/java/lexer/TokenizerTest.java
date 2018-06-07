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
        file.delete();
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
        tokenizer = new Tokenizer(createFileWithString("Game game1(2,0,0)\n" +
                "Player player1(\"Dima\", 1000)\n" +
                "print(\"player1 balance is: \")\n" +
                "println(player1.balance)\n" +
                "player1.joinGame(game1,100)\n" +
                "print(\"player1 balance is: \")\n" +
                "println(player1.balance)\n" +
                "player1.leaveGame(game1)"+
                "$"));


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