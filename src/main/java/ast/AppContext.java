package ast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eoller on 05-Jun-18.
 */
public class AppContext {
    public final static Map<String, Variable> variables = new HashMap<>();

    public boolean addVariable(String name, Variable variable){
        if(variables.containsKey(name)){
            return false;
        }else {
            variables.put(name, variable);
            return true;
        }
    }

    public void updateVariable(String name, Variable variable){
        variables.replace(name, variable);
    }

    public Variable getVariable(String name){
        return variables.get(name);
    }

    public boolean containsIdentifier(String identifier) {
        return variables.containsKey(identifier);
    }

}
