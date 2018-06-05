package model;

public class Token {

    private TokenType tokenType;
    private String value;
    //[QUESTION] DO I NEED POS.col, POS.row

    public Token() {
    }

    public Token(TokenType tokenType, String repr) {
        this.tokenType = tokenType;
        this.value = repr;
    }

    public TokenType getType() {
        return tokenType;
    }

    public void setType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
