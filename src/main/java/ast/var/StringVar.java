package ast.var;

import ast.Comparable;
/**
 * Created by Yahor_Melnik on 10-May-18.
 */
public class StringVar extends Variable implements Comparable {

    private StringBuilder value;

    public StringVar(String value) {
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
        if(object instanceof StringVar){
            StringVar stringVariable = (StringVar) object;
            return toString().compareTo(stringVariable.toString());
        }else {
            throw new RuntimeException("Error. Comparing StringVariable with a non-StringVariable type");
        }
    }
}
