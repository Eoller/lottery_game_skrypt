package parser;

import ast.*;
import ast.constvar.ConstBool;
import ast.constvar.ConstInt;
import ast.constvar.ConstString;
import ast.definition.GameDef;
import ast.definition.PlayerDef;
import ast.definition.PrimitiveDef;
import ast.var.VariableType;
import lexer.Tokenizer;
import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.List;

import static model.TokenType.*;

/**
 * Created by Yahor_Melnik on 10-May-18.
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
                TokenType.GAME_TYPE, TokenType.PLAYER_TYPE)){
            return parseVariableDefinition();
        }

        switch (current.getType()) {
            case IF:
                return parseIf();
            case WHILE:
                return parseWhile();
            case IDENTIFIER:
                return parseVariableOperationOrEmdedVarInitializationOrEmbededFunctionCall();
            default:
                throw new RuntimeException(createErrorMessage(TokenType.IF, TokenType.IDENTIFIER, TokenType.WHILE));
        }
    }

    private Instruction parseVariableOperationOrEmdedVarInitializationOrEmbededFunctionCall() throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        switch (current.getType()){
            case PERIOD:
                return parseAccess(identifier);
            case ASSIGN_OP:
                return parseAssignment(identifier);
            case OPEN_BRACE:
                return parseFunctionCall(identifier);
                default:
                    throw new RuntimeException(createErrorMessage(TokenType.PERIOD, TokenType.ASSIGN_OP));
        }
    }

    private Instruction parseFunctionCall(Identifier identifier) throws Exception {
        return new FunctionCall(parseFunctionCallArguments(), identifier);
    }


    private Instruction parseWhile() throws Exception {
        accept(TokenType.WHILE);
        accept(TokenType.OPEN_BRACE);

        Unit expression = parseExpression();

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
        return new GameDef(identifier.getName(),variableType, parseGameInit());
    }

    private Unit parseGameInit() throws Exception {
        accept(TokenType.OPEN_BRACE);;
        accept(TokenType.CLOSED_BRACE);
        return new GameInitParams();
    }

    private Instruction parsePlayerTypeDefinition() throws Exception {
        VariableType variableType = parseType(current);
        accept(TokenType.PLAYER_TYPE);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        return new PlayerDef(identifier.getName(), variableType, parsePlayerInit(identifier));
    }

    private Unit parsePlayerInit(Identifier identifier) throws Exception {
        accept(TokenType.OPEN_BRACE);
        Unit playerName = parseIdentifierOrAccessOrConstString();
        accept(TokenType.COMMA);
        Unit balance = parseIdentifierOrAccessOrConstInteger();
        accept(TokenType.CLOSED_BRACE);
        return new PlayerInitParams(playerName, balance);
    }

    private Unit parseIdentifierOrAccessOrConstInteger() throws Exception {
        switch (current.getType()){
            case CONST_INT:
                Integer value = Integer.valueOf(current.getValue());
                accept(TokenType.CONST_INT);
                return new ConstInt(value);
            case IDENTIFIER:
                return parseIdentifierOrAccess();
                default:
                    throw new RuntimeException("Error. Bad parameter for Player init");

        }
    }

    private Unit parseIdentifierOrAccessOrConstString() throws Exception {
        switch (current.getType()){
            case CONST_STRING:
                String value = current.getValue();
                accept(TokenType.CONST_STRING);
                return new ConstString(value);
            case IDENTIFIER:
                return parseIdentifierOrAccess();
                default:
                    throw new RuntimeException("Error. Bad parameter for Player init");
        }
    }

    private Instruction parsePrimitiveDefinition() throws Exception {
        VariableType variableType = parseType(current);
        accept(TokenType.INT_TYPE, TokenType.STRING_TYPE, TokenType.BOOL_TYPE);
        Identifier identifier = new Identifier(current.getValue());
        accept(TokenType.IDENTIFIER);
        return new PrimitiveDef(variableType, identifier.getName(), parseAssignment(identifier));
    }

    private Instruction parseAssignment(Identifier identifier) throws Exception {
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

        Unit expression = parseExpression();

        accept(TokenType.CLOSED_BRACE);

        Program body = parseInstructionBlock();

        if (current.getType() == TokenType.ELSE) {
            accept(TokenType.ELSE);
            return new If(expression, body, new Else(parseInstructionBlock()));
        }

        return new If(expression, body, null);
    }

    private Unit parseExpression() throws Exception {
        return parseLogicalExpression();
    }

    private Unit parseRelationExpression() throws Exception {
        Unit unit = parseLowPriorityArithmeticExpression();

        while (isAcceptable(TokenType.LESS, TokenType.LESS_EQUAL,
                TokenType.EQUAL, TokenType.NOT_EQUAL,
                TokenType.GREATER, TokenType.GREATER_EQUAL)) {

            TokenType type = current.getType();

            accept(TokenType.LESS, TokenType.LESS_EQUAL,
                    TokenType.EQUAL, TokenType.NOT_EQUAL,
                    TokenType.GREATER, TokenType.GREATER_EQUAL);
            unit = new Expression(unit, type, parseLowPriorityArithmeticExpression());

        }

        return unit;
    }

    private Unit parseLowPriorityArithmeticExpression() throws Exception {
        Unit unit = parseHighPriorityArithmeticExpression();

        while (isAcceptable(TokenType.PLUS, TokenType.MINUS)) {
            TokenType type = current.getType();
            accept(TokenType.PLUS, TokenType.MINUS);
            unit = new Expression(unit, type, parseHighPriorityArithmeticExpression());
        }

        return unit;
    }

    private Unit parseHighPriorityArithmeticExpression() throws Exception {
        Unit unit = parseOperandOrExpression();

        while (isAcceptable(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType type = current.getType();
            accept(TokenType.MULTIPLY, TokenType.DIVIDE);
            unit = new Expression(unit, type, parseOperandOrExpression());
        }

        return unit;
    }

    private Unit parseOperandOrExpression() throws Exception {
        if (current.getType() == TokenType.OPEN_BRACE) {
            accept(TokenType.OPEN_BRACE);
            Unit unit = parseExpression();
            accept(TokenType.CLOSED_BRACE);
            return unit;
        } else
            return parseOperand();
    }

    private Unit parseOperand() throws Exception {
        if(current.getType() == TokenType.IDENTIFIER)
            return parseIdentifierOrAccess();
        else
            return parseConstValue();
    }

    private Unit parseIdentifierOrAccess() throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        Unit unit = identifier;
        accept(TokenType.IDENTIFIER);

        if (current.getType() == TokenType.PERIOD)
            return parseAccess(unit);
        else
            return unit;
    }

    private Instruction parseAccess(Unit from) throws Exception { //должно возвращать сына Node у котого в run будет кто-то из детей Variable
        accept(TokenType.PERIOD);
        Unit identifier = parseEmdedVarOrEmbededFunctionCall(from);

        Instruction fromAccess = new Access(from, identifier);
        return fromAccess;
    }

    private Unit parseEmdedVarOrEmbededFunctionCall(Unit from) throws Exception {
        Identifier identifier = new Identifier(current.getValue());
        Unit unit = identifier;
        accept(TokenType.BALANCE, TokenType.BANK, TokenType.FIND_WINNER,TokenType.GAME_TYPE, TokenType.PLAYER_TYPE,
                TokenType.PLAYER_COUNT, TokenType.NEXT_ROUND,
                TokenType.NEXT_ROUND, TokenType.JOIN_GAME, TokenType.LEAVE_GAME,
                TokenType.NAME, TokenType.START_GAME, TokenType.STATUS, TokenType.WINER);
        if(current.getType() == TokenType.OPEN_BRACE){
            unit = new EmbededFunctionCall(parseFunctionCallArguments(),(Identifier) from, identifier);
            return unit;
        }
        return unit;

    }

    private List<Unit> parseFunctionCallArguments() throws Exception {
        ArrayList<Unit> args = new ArrayList<>();
        accept(TokenType.OPEN_BRACE);
        while (current.getType() != TokenType.EOF) {
            Unit arg;
            if (current.getType() == TokenType.CLOSED_BRACE) {
                accept(TokenType.CLOSED_BRACE);
                return args;
            } else {
                arg = parseExpression();
            }
            args.add(arg);
            if (parseEndOfArgsOrComma())
                return args;
        }
        return args;
    }

    private boolean parseEndOfArgsOrComma() throws Exception {
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

    private Unit parseConstValue() throws Exception {
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

    private Unit parseLogicalExpression() throws Exception {
        Unit unit = parseRelationExpression();

        while (isAcceptable(TokenType.AND, TokenType.OR)) {
            TokenType type = current.getType();
            accept(TokenType.AND, TokenType.OR);
            unit = new Expression(unit, type, parseRelationExpression());
        }

        return unit;
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
                .append("Error: ").append("Unexpected token: ").append(current.getValue())
                .append(" in line: ").append(lexer.getLineNumber()).append(" in column: ").append(lexer.getColNumber());

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
