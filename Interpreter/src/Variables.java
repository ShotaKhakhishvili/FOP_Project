import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variables<T>{
    private Map<String,T> map = new HashMap<>();
    private Set<Character> invalidChars = new HashSet<>(Set.of(
            '@', '#', '%', '^', '&', '*', '(', ')', '-', '+', '=', '{', '}',
            '[', ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?',
            '/', '~', '`', ' '
    ));

    public void declareVariable(String varName, T varValue){
        if(map.containsKey(varName))
            throw new RuntimeException("Variable '" + varName + "' is already declared");

        if(!checkValidity(varName))
            throw new RuntimeException("Illegal variable name declaration on line " + (Runner.pc + 1));

        map.put(varName, varValue);
    }

    public void setValue(String varName, T varValue){
        if(map.containsKey(varName))
            map.put(varName,varValue);
        else
            throw new RuntimeException("Variable '" + varName + "' is not declared on line " + (Runner.pc + 1));
    }

    public T getValue(String varName){
        if(map.containsKey(varName)) {
            if(map.get(varName) == null)
                throw new RuntimeException("Variable '" + varName + "' is null on line " + (Runner.pc + 1));
            return map.get(varName);
        }
        else
            throw new RuntimeException("Variable '" + varName + "' is not declared on line " + (Runner.pc + 1));
    }

    public boolean containsElement(String varName){
        return map.containsKey(varName);
    }

    public boolean checkValidity(String varName){
        if(varName.charAt(0) <= '9' && varName.charAt(0) >= '0') return false;

        for(char ch : varName.toCharArray()){
            if(invalidChars.contains(ch))return false;
        }

        return true;
    }
}
