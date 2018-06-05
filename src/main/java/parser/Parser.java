package parser;

import ast.*;
import lexer.Tokenizer;
import model.Token;
import model.TokenType;

import javax.xml.bind.Element;

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

    public Program parse() {
        Program program = new Program();

        Instruction lastParsedInstruction;

        while (current.getType() != TokenType.EOF && (lastParsedInstruction = parseInstruction()) != null) {
            program.addInstruction(lastParsedInstruction);
        }

        return program;
    }

    private Instruction parseInstruction() throws Exception {
        switch (current.getType()) {

            case IF:
                return parseIf();
            default:
                throw new RuntimeException(createErrorMessage(TokenType.IF, TokenType.IDENTIFIER, TokenType.WHILE));
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

    private Node parseConstValue() throws Exception {
        switch (current.getType()){
            case CONST_INT:
                String value = current.getValue();
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
