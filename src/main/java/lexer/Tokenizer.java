package lexer;

import file.FileScanner;
import file.Scanner;
import model.Token;
import model.Type;

import java.io.File;
import java.util.HashMap;

public class Tokenizer {

    private Scanner scanner;

    public final static HashMap<String, Token> embededWords = new HashMap<String, Token>();

    public Tokenizer(File file) {
        scanner = new FileScanner(file);
        initEmbededWords();
    }

    public Token getNextToken(){
        //TODO empty input exeption; [QUESTION] WHERE DO I NEED TO CHECK EMPTY INPUT
        char curr = scanner.getNextChar();
        while (Character.isWhitespace(curr) && !scanner.isFinished()){
            curr = scanner.getNextChar();
        }
        return processChar(curr);
    }

    private Token processChar(char curr) {
        if(scanner.isFinished()){
            return endOfFile();
        }
        if(Character.isLetter(curr)){
            return tryToParseIdentifierOrKeyword(curr);
        }else if(Character.isDigit(curr)){
            return tryToParseNumber(curr);
        }else if (curr == '&' || curr == '|'){
            return tryToParseLogicalOperator(curr);
        }else if (curr == '=' || curr == '!' || curr == '<' || curr == '>'){
            return tryToParseComparisonOperator(curr);
        }else if (curr == '\"'){
            return tryToParseString(curr);
        }else{
            return tryToParseCharOperator(curr);
        }

    }

    public Token tryToParseNumber(char currentSymbol){
        StringBuilder out = new StringBuilder();
        out.append(currentSymbol);

        while (Character.isDigit(scanner.showMeCurrentChar()) && !scanner.isFinished()){
            out.append(scanner.getNextChar());
        }

        return new Token(Type.INT_INST, out.toString());
    }

    private Token tryToParseIdentifierOrKeyword(char currentSymbol){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(currentSymbol);
        while (Character.isLetter(scanner.showMeCurrentChar()) || Character.isDigit(scanner
                .showMeCurrentChar()) && !scanner.isFinished()){
            stringBuilder.append(scanner.getNextChar());
        }
        String parsed = stringBuilder.toString();
        if(embededWords.containsKey(parsed)){
            return embededWords.get(parsed);
        }
        return new Token(Type.IDENTIFIER, parsed);
    }

    private Token tryToParseComparisonOperator(char currentSymbol){
        StringBuilder out = new StringBuilder();

        out.append(currentSymbol);

        if(scanner.showMeCurrentChar() == '='){
            out.append(scanner.getNextChar());
        }else {
            if(currentSymbol == '!'){
                return error(scanner.showMeCurrentChar());
            }
        }

        return embededWords.get(out.toString());
    }

    private Token tryToParseLogicalOperator(char currentSymbol){
        StringBuilder out = new StringBuilder();

        out.append(currentSymbol);

        if(scanner.showMeCurrentChar() == '&' && currentSymbol == '&' || scanner.showMeCurrentChar() == '|' && currentSymbol == '|'){
            out.append(scanner.showMeCurrentChar());
        }else {
            return error(scanner.showMeCurrentChar());
        }

        return embededWords.get(out.toString());
    }

    private Token tryToParseCharOperator(char currentSymbol){
        Token token = embededWords.get(Character.toString(currentSymbol));

        if(token == null){
            return error(currentSymbol);
        }

        return token;
    }

    private Token tryToParseString(char currentSymbol){
        StringBuilder out = new StringBuilder();

        out.append(currentSymbol);

        while (scanner.showMeCurrentChar() != '\"' && !scanner.isFinished()){
            out.append(scanner.getNextChar());
        }
        if(!scanner.isFinished()){
            out.append(scanner.getNextChar());
        }

        return new Token(Type.STRING_INST, out.toString());
    }

    private Token endOfFile(){
        return new Token(Type.END_OF_FILE, "end of file");
    }

    private Token error(char currentChar){
        return new Token(Type.EMPTY, "Unexpected char"); //TODO refactor method
    }

    private void initEmbededWords() {
        embededWords.put("Game", new Token(Type.GAME, "Game"));
        embededWords.put("int", new Token(Type.INT, "int"));
        embededWords.put("bool", new Token(Type.BOOL, "bool"));
        embededWords.put("Player", new Token(Type.PLAYER, "Player"));
        embededWords.put("String", new Token(Type.STRING, "String"));
        embededWords.put("if", new Token(Type.IF, "if"));
        embededWords.put("else", new Token(Type.ELSE, "else"));
        embededWords.put("=", new Token(Type.EQUALLY, "="));
        embededWords.put("true", new Token(Type.BOOL_INST, "true"));
        embededWords.put("false", new Token(Type.BOOL_INST, "false"));
        embededWords.put(".", new Token(Type.DOT, "."));
        embededWords.put(",", new Token(Type.COMMA , ","));
        embededWords.put(";", new Token(Type.SEMICOLON , ";"));
        embededWords.put("wait", new Token(Type.WAIT, "wait"));
        embededWords.put("while", new Token(Type.WHILE, "while"));

        embededWords.put("*", new Token(Type.ARITHMETIC_OPERATOR, "*"));
        embededWords.put("/", new Token(Type.ARITHMETIC_OPERATOR, "/"));
        embededWords.put("+", new Token(Type.ARITHMETIC_OPERATOR, "+"));
        embededWords.put("-", new Token(Type.ARITHMETIC_OPERATOR, "-"));

        embededWords.put("(", new Token(Type.OPEN_BRACE, "("));
        embededWords.put(")", new Token(Type.CLOSED_BRACE, ")"));

        embededWords.put("{", new Token(Type.OPEN_CURLY_BRACE, "{"));
        embededWords.put("}", new Token(Type.CLOSED_CURLY_BRACE, "}"));

        embededWords.put("!=", new Token(Type.RELATIONAL_OPERATOR, "!="));
        embededWords.put("<", new Token(Type.RELATIONAL_OPERATOR, "<"));
        embededWords.put(">", new Token(Type.RELATIONAL_OPERATOR, ">"));
        embededWords.put("<=", new Token(Type.RELATIONAL_OPERATOR, "<="));
        embededWords.put(">=", new Token(Type.RELATIONAL_OPERATOR, ">="));
        embededWords.put("==", new Token(Type.RELATIONAL_OPERATOR, "=="));

        embededWords.put("&&", new Token(Type.LOGICAL_OPERATOR, "&&"));
        embededWords.put("||", new Token(Type.LOGICAL_OPERATOR, "||"));

        embededWords.put("status", new Token(Type.GAME_ATTR, "status"));
        embededWords.put("player_count", new Token(Type.GAME_ATTR, "player_count"));
        embededWords.put("bank", new Token(Type.GAME_ATTR, "bank"));
        embededWords.put("winner", new Token(Type.GAME_ATTR, "winner"));

        embededWords.put("name", new Token(Type.PLAYER_ATTR, "name"));
        embededWords.put("balance", new Token(Type.PLAYER_ATTR, "balance"));

        embededWords.put("join_game", new Token(Type.PLAYER_FUN, "join_game"));
        embededWords.put("leave_game", new Token(Type.PLAYER_FUN, "leave_game"));

        embededWords.put("start_game", new Token(Type.GAME_FUN, "start_game"));
        embededWords.put("end_game", new Token(Type.GAME_FUN, "end_game"));
        embededWords.put("find_winner", new Token(Type.GAME_FUN, "find_winner"));

    }
}
