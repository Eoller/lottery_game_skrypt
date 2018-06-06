package model;

/**
 * Created by unity on 18.04.18.
 */
public enum TokenType {
    INT_TYPE, BOOL_TYPE, STRING_TYPE, GAME_TYPE, PLAYER_TYPE,
    IF,ELSE,
    WHILE,
    EOF,
    EMPTY,
    IDENTIFIER,
    CONST_BOOL,
    TRUE, FALSE,

    EQUAL,
    OPEN_BRACE, CLOSED_BRACE, OPEN_CURLY_BRACE, CLOSED_CURLY_BRACE,
    PERIOD, SEMICOLON, COMMA,

    LESS, LESS_EQUAL, ASSIGN_OP, NOT_EQUAL, GREATER, GREATER_EQUAL,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    AND, OR,



    //GRA
    START_GAME, END_GAME, FIND_WINNER,
    STATUS, BANK, WINER, PLAYER_COUNT,

    //PLAYER
    JOIN_GAME, LEAVE_GAME,
    BALANCE, CONST_STRING, CONST_INT, NAME
}
