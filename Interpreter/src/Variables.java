import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Variables<T>{
    private Map<String,T> map = new HashMap<>();

    public void declareVariable(String varName, T varValue){
       if(!LoopHandler.stack.isEmpty()){
            LoopHandler.stack.peek().getThird().add(varName);
        }

        map.put(varName, varValue);
    }

    public void deleteVariable(String varName){
        if(!map.containsKey(varName))
            throw new RuntimeException("Variable '" + varName + "' is not declared");

        LoopHandler.stack.peek().getThird().remove(varName);
        map.remove(varName);
    }

    public void setValue(String varName, T varValue){
        if(map.containsKey(varName))
            map.put(varName,varValue);
        else
            System.out.println("error");
    }

    public T getValue(String varName) throws CompilationError {
        if(varName == null)
            throw new RuntimeException("Variable '" + varName + "' is null");
        if(map.containsKey(varName)) {
            if(map.get(varName) == null)
                throw new RuntimeException("Variable '" + varName + "' is null");
            return map.get(varName);
        }
        else
            throw new CompilationError("Variable '" + varName + "' is not declared");
    }

    public boolean containsElement(String varName){
        return map.containsKey(varName);
    }

    public Map<String, T> getMap() {
        return map;
    }

    public static boolean checkValidity(String varName){
        if(varName.charAt(0) <= '9' && varName.charAt(0) >= '0') return false;

        for(char ch : varName.toCharArray()){
            if(InstructionHandler.invalidChars.contains(ch))return false;
        }

        return true;
    }
}
