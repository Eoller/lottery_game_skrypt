package usecase;

import ast.var.GameVar;
import ast.var.IntegerVar;
import ast.var.PlayerVar;
import ast.var.StringVar;
import intepreter.Intepreter;
import lexer.Tokenizer;
import model.AppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Eoller on 3-Jun-18.
 */
public class UsecaseTest {

    private File file;

    private Intepreter intepreter;

    @Before
    public void initIntepreter() throws Exception {
        intepreter = new Intepreter();
        AppContext.variables.clear();
    }

    @After
    public void clear(){
        file.delete();
    }

    @Test
    public void shouldPrintInt() throws Exception {
        String code = "int a = 5" +
                "print(a)" +
                "$";
        intepreter.run(createFileWithString(code));
    }

    @Test
    public void shouldPrintString() throws Exception {
        String code = "String a = \"Hello world\"" +
                "print(a)" +
                "$";
        intepreter.run(createFileWithString(code));
    }


    @Test
    public void shouldWorkPlayerAccess() throws Exception {
        String code = "Player a(\"Lolek\", 1000)\n" +
                "int b = a.balance\n" +
                "String c = a.name\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(1000, ((PlayerVar)AppContext.getVariable("a")).getBalance().intValue());
        assertEquals("Lolek", ((PlayerVar)AppContext.getVariable("a")).getPlayerName().toString());
    }

    @Test
    public void shouldWorkGame() throws Exception {
        String code = "Game a()\n" +
                "int b = a.status\n" +
                "int c = a.bank\n" +
                "int d = a.playerCount\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(0, ((GameVar)AppContext.getVariable("a")).getBank().intValue());
        assertEquals(0, ((GameVar)AppContext.getVariable("a")).getPlayerCount().intValue());
        assertEquals(0, ((GameVar)AppContext.getVariable("a")).getStatus().intValue());
    }

    @Test
    public void shouldWorkAddingPlayerToGame() throws Exception {
        String code = "Game a()\n" +
                "Player player1(\"Dima\", 500)\n" +
                "Player player2(\"Piotr\", 300)\n" +
                "\n" +
                "player1.joinGame(a, 100)\n" +
                "player2.joinGame(a, 200)\n" +
                "\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(300, ((GameVar)AppContext.getVariable("a")).getBank().intValue());
        assertEquals(2, ((GameVar)AppContext.getVariable("a")).getPlayerCount().intValue());
        assertEquals(0, ((GameVar)AppContext.getVariable("a")).getStatus().intValue());
        assertEquals(400, ((PlayerVar)AppContext.getVariable("player1")).getBalance().intValue());
        assertEquals(100, ((PlayerVar)AppContext.getVariable("player2")).getBalance().intValue());
    }

    @Test
    public void shouldWorkDeletingplayerFromGame() throws Exception {
        String code = "Game ga()\n" +
                "Player player1(\"Dima\", 500)\n" +
                "Player player2(\"Piotr\", 300)\n" +
                "\n" +
                "player1.joinGame(ga, 100)\n" +
                "player2.joinGame(ga, 200)\n" +
                "\n" +
                "player1.leaveGame(ga)\n" +
                "\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(1, ((GameVar)AppContext.getVariable("ga")).getPlayerCount().intValue());
    }

    @Test
    public void shouldWorkGameFindWinner() throws Exception {
        AppContext.variables.clear();
        String code = "Game ss()\n" +
                "Player player1(\"Dima\", 500)\n" +
                "Player player2(\"Piotr\", 300)\n" +
                "\n" +
                "player1.joinGame(ss, 30)\n" +
                "player2.joinGame(ss, 200)\n" +
                "\n" +
                "ss.nextRound()\n" +
                "ss.findWinner()\n" +
                "\n" +
                "if(ss.winner == \"player1\"){\n" +
                "\tprint(\"player1 is winner and his balance = \")\n" +
                "    println(player1.balance)\n" +
                "    print(\"and player2 balance = \")\n" +
                "    println(player2.balance)\n" +
                "}else{\n" +
                "    print(\"player2 is winner and his balance = \")\n" +
                "    println(player2.balance)\n" +
                "    print(\"and player1 balance = \")\n" +
                "    println(player1.balance)\n" +
                "}\n" +
                "$\n" +
                "    ";
        intepreter.run(createFileWithString(code));
        assertEquals(2, ((GameVar)AppContext.getVariable("ss")).getPlayerCount().intValue());
        assertEquals(0, ((GameVar)AppContext.getVariable("ss")).getBank().intValue());
        assertEquals(1, ((GameVar)AppContext.getVariable("ss")).getStatus().intValue());
        assertNotEquals("", ((GameVar)AppContext.getVariable("ss")).getWinner());
    }

    @Test
    public void shouldIfWork() throws Exception {
        String code = "int a = 5\n" +
                "bool x = true\n" +
                "if(x && (5 > 3)){\n" +
                "\ta = 20\n" +
                "}\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(20, ((IntegerVar)AppContext.getVariable("a")).getValue().intValue());

    }

    @Test
    public void shouldWhileWork() throws Exception {
        String code = "int a = 5\n" +
                "while(a < 100){\n" +
                "\ta = a + 5\n" +
                "}\n" +
                "$";
        intepreter.run(createFileWithString(code));
        assertEquals(100, ((IntegerVar)AppContext.getVariable("a")).getValue().intValue());

    }





    private File createFileWithString(String data) {
        try {
            file = new File("ForUseCaseTest.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {}
        return file;
    }
}
