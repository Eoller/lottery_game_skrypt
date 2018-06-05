package parser;

import ast.*;
import lexer.Tokenizer;
import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.List;

import static model.TokenType.*;

/**
 * Created by Eoller on 03-Jun-18.
 */
public class Parser {
    private Tokenizer lexer;
    private Token current;

    public Parser(final Tokenizer lexer) {
        this.lexer = lexer;
        current = lexer.nextToken();
    }

    public Program parse() throws Exception {
        Program program = new Program();

        Instruction lastParsedInstruction;

        while (current.getType() != TokenType.EOF && (lastParsedInstruction = parseInstruction()) != null) {
            program.addInstruction(lastParsedInstruction);
        }

        return program;
    }

    private Instruction parseInstruction() throws Exception {
        if(isAcceptable(INT_TYPE, TokenType.STRING_TYPE,TokenType.BOOL_TYPE,
                TokenType.GAME_TYPE, TokenType.PLAYER_TYPE)){ //TODO
            return parseVariableDefinition();
        }

        switch (current.getType()) {
            case IF:
                return parseIf();
            case WHILE:
                return parseWhile();
            case IDENTIFIER:
                //return parseEmdedVarInitializationOrEmbededFunctionCall(); //TODO
            default:
                throw new RuntimeException(createErrorMessage(TokenType.IF, TokenType.IDENTIFIER, TokenType.WHILE));
        }
    }

    private Instruction parseWhile() throws Exception {
        accept(TokenType.WHILE);
        accept(TokenType.OPEN_BRACE);

        Node expression = parseExpression();

        accept(TokenType.CLOSED_BRACE);

        return new While(expression, parseInstructionBlock());
    }

    private Instruction parseVariableDefinition() throws Exception {
        switch (current.getType()){
            case INT_TYPE:
                return parsePrimitiveDefinition();
            case STRING_TYPE:
                return parsePrimitiveDefinition();
            case BOOL_TYPE:
                return parsePrimitiveDefinition();
            case GAME_TYPE:
                return parseGameTypeDefinition();
            case PLAYER_TYPE:
                return parsePlayerTypeDefinition();
        }

        return null;
    }

    private Instruction parseGameTypeDefinition() throws Exception {
        VariableType variableType = parseType(current);
        accept(TokenType.GAME_TYPE);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        accept(TokenType.OPEN_BRACE);
        int player_count = Integer.parseInt(current.getValue());
        accept(TokenType.CONST_INT);
        accept(TokenType.COMMA);
        int bank = Integer.parseInt(current.getValue());
        accept(TokenType.CONST_INT);
        accept(TokenType.COMMA);
        int status = Integer.parseInt(current.getValue());
        accept(TokenType.CONST_INT);
        accept(TokenType.CLOSED_BRACE);
        return new GameDefinition(identifier.getName(), variableType, bank, status, player_count);
    }

    private Instruction parsePlayerTypeDefinition() throws Exception {
        VariableType variableType = parseType(current);
        accept(TokenType.PLAYER_TYPE);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        accept(TokenType.OPEN_BRACE);
        String player_name = current.getValue();
        accept(TokenType.CONST_STRING);
        accept(TokenType.COMMA);
        int balance = Integer.parseInt(current.getValue());
        accept(TokenType.CONST_INT);
        accept(TokenType.CLOSED_BRACE);
        return new PlayerDefinition(identifier.getName(), variableType, player_name, balance);
    }

    private Instruction parsePrimitiveDefinition() throws Exception {
        VariableType variableType = parseType(current);
        accept(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        return new PrimitiveDefinition(variableType, identifier.getName(), parseAssignment(identifier));
    }

    private Node parseAssignment(Identifier identifier) throws Exception {
        accept(TokenType.ASSIGN_OP);
        return new Assignment(parseExpression(), identifier);
    }

    private VariableType parseType(Token token) throws Exception {
        switch (token.getType()) {
            case INT_TYPE:
                return VariableType.INT;
            case STRING_TYPE:
                return VariableType.STRING;
            case BOOL_TYPE:
                return VariableType.BOOL;
            case GAME_TYPE:
                return VariableType.GAME;
            case PLAYER_TYPE:
                return VariableType.PLAYER;
            default:
                throw new RuntimeException(createErrorMessage(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE, TokenType.PLAYER_TYPE, TokenType.GAME_TYPE));
        }
    }

    private Instruction parseIf() throws Exception {
        accept(TokenType.IF);
        accept(TokenType.OPEN_BRACE);

        Node expression = parseExpression();

        accept(TokenType.CLOSED_BRACE);

        Program body = parseInstructionBlock();

        if (current.getType() == TokenType.ELSE) {
            accept(TokenType.ELSE);
            return new If(expression, body, new Else(parseInstructionBlock()));
        }

        return new If(expression, body, null);
    }

    private Node parseExpression() throws Exception {
        return parseLogicalExpression();
    }

    private Node parseRelationExpression() throws Exception {
        Node node = parseLowPriorityArithmeticExpression();

        while (isAcceptable(TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.EQUAL, TokenType.NOT_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL)) {

            TokenType type = current.getType();

            accept(TokenType.LESS, TokenType.LESS_EQUAL,
                    TokenType.EQUAL, TokenType.NOT_EQUAL,
                    TokenType.GREATER, TokenType.GREATER_EQUAL);
            node = new Expression(node, type, parseLowPriorityArithmeticExpression());

        }

        return node;
    }

    private Node parseLowPriorityArithmeticExpression() throws Exception {
        Node node = parseHighPriorityArithmeticExpression();

        while (isAcceptable(TokenType.PLUS, TokenType.MINUS)) {
            TokenType type = current.getType();
            accept(TokenType.PLUS, TokenType.MINUS);
            node = new Expression(node, type, parseHighPriorityArithmeticExpression());
        }

        return node;
    }

    private Node parseHighPriorityArithmeticExpression() throws Exception {
        Node node = parseOperandOrExpression();

        while (isAcceptable(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType type = current.getType();
            accept(TokenType.MULTIPLY, TokenType.DIVIDE);
            node = new Expression(node, type, parseOperandOrExpression());
        }

        return node;
    }

    private Node parseOperandOrExpression() throws Exception {
        if (current.getType() == TokenType.OPEN_BRACE) {
            accept(TokenType.OPEN_BRACE);
            Node node = parseExpression();
            accept(TokenType.CLOSED_BRACE);
            return node;
        } else
            return parseOperand();
    }

    private Node parseOperand() throws Exception {
        if(current.getType() == TokenType.IDENTIFIER)
            return parseIdentifierOrAccess();
        else
            return parseConstValue();
    }

    private Node parseIdentifierOrAccess() throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        Node node = identifier;
        accept(TokenType.IDENTIFIER);

        if (current.getType() == TokenType.PERIOD)
            return parseAccess(node);
        else
            return node;
    }

    private Instruction parseAccess(Node from) throws Exception {
        accept(TokenType.PERIOD);
        Node identifier = parseEmdedVarOrEmbededFunctionCall(); //тут должно быть что-то что вернёт либо
        // ембедед вар с екзекутом который потом будем использовать в Access, либо
        // ембедед функцию с екзекутом результат которой потом будем использовать в Access

        Instruction fromAccess = new Access(from, identifier);
        return fromAccess;
    }

    private Node parseEmdedVarOrEmbededFunctionCall() throws Exception {
        String name = current.getValue();
        Node node;
        accept(TokenType.BALANCE, TokenType.BANK, TokenType.FIND_WINNER,TokenType.GAME_TYPE, TokenType.GAME_TYPE,
                TokenType.END_GAME, TokenType.JOIN_GAME, TokenType.LEAVE_GAME,
                TokenType.NAME, TokenType.START_GAME, TokenType.STATUS, TokenType.WINER);
        if(current.getType() == TokenType.OPEN_BRACE){
            node = new EmbededFunctionCall(parseFunctionCallArguments(), name);
            return node;
        }
        node = new EmbededVar(name);
        return node;

    }

    private List<Node> parseFunctionCallArguments() throws Exception {
        ArrayList<Node> args = new ArrayList<>();
        accept(TokenType.OPEN_BRACE);
        while (current.getType() != TokenType.EOF) {
            Node arg;
            if (current.getType() == TokenType.CLOSED_BRACE) {
                accept(TokenType.CLOSED_BRACE);
                return args;
            } else {
                arg = parseExpression();
            }
            args.add(arg);
            if (parseEndOfArgsOfComma())
                return args;
        }
        return args;
    }

    private boolean parseEndOfArgsOfComma() throws Exception {
        if(current.getType() == TokenType.COMMA){
            accept(TokenType.COMMA);
        }else if(current.getType() == TokenType.CLOSED_BRACE){
            accept(TokenType.CLOSED_BRACE);
            return true;
        }else{
            accept(TokenType.COMMA, TokenType.CLOSED_BRACE);
        }
        return false;
    }

    private Node parseConstValue() throws Exception {
        switch (current.getType()){
            case CONST_INT:
                String value = current.getValue();
                accept(TokenType.CONST_INT);
                return new ConstInt(Integer.parseInt(value));
            case CONST_BOOL:
                ConstBool bool = new ConstBool((current.getValue().equals("true")));
                accept(TokenType.CONST_BOOL);
                return bool;
            case CONST_STRING:
                String string = current.getValue();
                accept(TokenType.CONST_STRING);
                return new ConstString(string);
            default:
                throw new RuntimeException(createErrorMessage(TokenType.CONST_INT, TokenType.CONST_BOOL, TokenType.CONST_STRING));
        }
    }

    private Node parseLogicalExpression() throws Exception {
        Node node = parseRelationExpression();

        while (isAcceptable(TokenType.AND, TokenType.OR)) {
            TokenType type = current.getType();
            accept(TokenType.AND, TokenType.OR);
            node = new Expression(node, type, parseRelationExpression());
        }

        return node;
    }

    private Program parseInstructionBlock() throws Exception {
        Program body = new Program();

        accept(TokenType.OPEN_CURLY_BRACE);

        while (current.getType() != TokenType.CLOSED_CURLY_BRACE && current.getType() != TokenType.EOF) {
            body.addInstruction(parseInstruction());
        }

        accept(TokenType.CLOSED_CURLY_BRACE);

        return body;
    }

    private String createErrorMessage(final TokenType... types) {
        StringBuilder stringBuilder = new StringBuilder()
                .append("Error: ")
                .append("Unexpected token: ");

        for (final TokenType tokenType : types) {
            stringBuilder.append(tokenType);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private void accept(final TokenType... types) throws Exception {
        if (isAcceptable(types))
            current = lexer.nextToken();
        else {
            final String errorMessage = createErrorMessage(types);
            System.err.println(errorMessage);
            throw new Exception(errorMessage);
        }
    }

    private boolean isAcceptable(final TokenType... types) {
        for (final TokenType type : types) {
            if (type.equals(current.getType())) {
                return true;
            }
        }
        return false;
    }
}
