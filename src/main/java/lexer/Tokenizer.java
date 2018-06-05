package lexer;

import file.FileScanner;
import file.Scanner;
import model.Token;
import model.TokenType;

import java.io.File;
import java.util.HashMap;

public class Tokenizer {

    private Scanner scanner;

    public final static HashMap<String, Token> embededWords = new HashMap<String, Token>();

    public Tokenizer(File file) {
        scanner = new FileScanner(file);
        initEmbededWords();
    }

    public Token nextToken(){
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

        return new Token(TokenType.INT_INST, out.toString());
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
        return new Token(TokenType.IDENTIFIER, parsed);
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

        return new Token(TokenType.STRING_INST, out.toString());
    }

    private Token endOfFile(){
        return new Token(TokenType.EOF, "end of file");
    }

    private Token error(char currentChar){
        return new Token(TokenType.EMPTY, "Unexpected char"); //TODO refactor method
    }

    private void initEmbededWords() {
        embededWords.put("Game", new Token(TokenType.GAME_TYPE, "Game"));
        embededWords.put("int", new Token(TokenType.INT_TYPE, "int"));
        embededWords.put("bool", new Token(TokenType.BOOL_TYPE, "bool"));
        embededWords.put("Player", new Token(TokenType.PLAYER_TYPE, "Player"));
        embededWords.put("String", new Token(TokenType.STRING_TYPE, "String"));
        embededWords.put("if", new Token(TokenType.IF, "if"));
        embededWords.put("else", new Token(TokenType.ELSE, "else"));
        embededWords.put("=", new Token(TokenType.ASSIGN_OP, "="));
        embededWords.put("true", new Token(TokenType.CONST_BOOL, "true"));
        embededWords.put("false", new Token(TokenType.CONST_BOOL, "false"));
        embededWords.put(".", new Token(TokenType.PERIOD, "."));
        embededWords.put(",", new Token(TokenType.COMMA , ","));
        embededWords.put(";", new Token(TokenType.SEMICOLON , ";"));
        embededWords.put("while", new Token(TokenType.WHILE, "while"));

        embededWords.put("*", new Token(TokenType.MULTIPLY, "*"));
        embededWords.put("/", new Token(TokenType.DIVIDE, "/"));
        embededWords.put("+", new Token(TokenType.PLUS, "+"));
        embededWords.put("-", new Token(TokenType.MINUS, "-"));

        embededWords.put("(", new Token(TokenType.OPEN_BRACE, "("));
        embededWords.put(")", new Token(TokenType.CLOSED_BRACE, ")"));

        embededWords.put("{", new Token(TokenType.OPEN_CURLY_BRACE, "{"));
        embededWords.put("}", new Token(TokenType.CLOSED_CURLY_BRACE, "}"));

        embededWords.put("!=", new Token(TokenType.NOT_EQUAL, "!="));
        embededWords.put("<", new Token(TokenType.LESS, "<"));
        embededWords.put(">", new Token(TokenType.GREATER, ">"));
        embededWords.put("<=", new Token(TokenType.LESS_EQUAL, "<="));
        embededWords.put(">=", new Token(TokenType.GREATER_EQUAL, ">="));
        embededWords.put("==", new Token(TokenType.EQUAL, "=="));

        embededWords.put("&&", new Token(TokenType.AND, "&&"));
        embededWords.put("||", new Token(TokenType.OR, "||"));

        embededWords.put("status", new Token(TokenType.STATUS, "status"));
        embededWords.put("player_count", new Token(TokenType.PLAYER_COUNT, "player_count"));
        embededWords.put("bank", new Token(TokenType.BANK, "bank"));
        embededWords.put("winner", new Token(TokenType.WINER, "winner"));

        embededWords.put("name", new Token(TokenType.NAME, "name"));
        embededWords.put("balance", new Token(TokenType.BALANCE, "balance"));

        embededWords.put("join_game", new Token(TokenType.JOIN_GAME, "join_game"));
        embededWords.put("leave_game", new Token(TokenType.LEAVE_GAME, "leave_game"));

        embededWords.put("start_game", new Token(TokenType.START_GAME, "start_game"));
        embededWords.put("end_game", new Token(TokenType.END_GAME, "end_game"));
        embededWords.put("find_winner", new Token(TokenType.FIND_WINNER, "find_winner"));

    }
}
