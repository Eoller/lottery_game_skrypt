package model;
/**
 * Created by Yahor_Melnik on 03-Jun-18.
 */
public class Token {

    private TokenType tokenType;
    private String value;

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
