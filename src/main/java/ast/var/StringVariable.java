package ast.var;

public class StringVariable extends Variable {

    private StringBuilder value;

    public StringVariable(String value) {
        this.value = new StringBuilder(value);
    }

    public void concat(String string) {
        value.append(string);
    }

    public StringBuilder getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public VariableType getType() {
        return VariableType.STRING;
    }
}
