package ast.var;

import ast.Comparable;

public class StringVariable extends Variable implements Comparable {

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

    @Override
    public int compare(Object object) {
        if(object instanceof StringVariable){
            StringVariable stringVariable = (StringVariable) object;
            return toString().compareTo(stringVariable.toString());
        }else {
            throw new RuntimeException("Error. Comparing StringVariable with a non-StringVariable type");
        }
    }
}
