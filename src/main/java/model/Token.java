package model;

public class Token {

    private Type type;
    private String repr;
    //[QUESTION] DO I NEED POS.col, POS.row

    public Token() {
    }

    public Token(Type type, String repr) {
        this.type = type;
        this.repr = repr;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRepr() {
        return repr;
    }

    public void setRepr(String repr) {
        this.repr = repr;
    }
}
