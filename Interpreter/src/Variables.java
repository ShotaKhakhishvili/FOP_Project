import java.util.HashMap;
import java.util.Map;

public class Variables<T> {
    private Map<String,T> map = new HashMap<>();

    public void declareVariable(String varName, T varValue){
        if(map.containsKey(varName))
            throw new RuntimeException("Variable '" + varName + "' is already declared");

        map.put(varName, varValue);
    }

    public void setValue(String varName, T varValue){
        if(map.containsKey(varName))
            map.put(varName,varValue);
        else
            throw new RuntimeException("Variable '" + varName + "' is not declared");
    }

    public T getValue(String varName){
        if(map.containsKey(varName))
            return map.get(varName);
        else
            throw new RuntimeException("Variable '" + varName + "' is not declared");
    }
}
