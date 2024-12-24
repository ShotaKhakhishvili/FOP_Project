import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface InstructionHandler {
    Map<String, Instruction> map = new HashMap<>() {{
        put("dim", Instruction.declaration);
        put("if", Instruction.iif);
        put("while", Instruction.wwhile);
        put("print", Instruction.print);
        put("input", Instruction.input);
        put("wend", Instruction.wend);
    }};
    Map<String, Assignment> mapAss = new HashMap<>() {{
        put("+", Assignment.add);
        put("-", Assignment.sub);
        put("*", Assignment.mult);
        put("/", Assignment.div);
        put("%", Assignment.mod);
    }};

    static String[] normalizeInstruction(String instruction){
        instruction = instruction.toLowerCase();
        char[] chars = instruction.toCharArray();
        char last = '0';
        String newInstruction = "";
        for(char curr : chars){
            if(!(last == ' ' && curr == ' '))
                newInstruction += curr;
            last = curr;
        }
        return newInstruction.split(" ");
    }

    static Instruction decode(String[] args){
        if(args.length == 0)return Instruction.invalid;

        if(map.containsKey(args[0]))
            return map.get(args[0]);

        if(args.length == 1)return Instruction.invalid;

        if(args[0].equals("end") && args[1].equals("if")) {
            if(args.length == 2) return Instruction.endif;
            return Instruction.invalid;
        }

        return Instruction.assignment;
    }

    static Assignment decodeAssignment(String[] args){
        if(args.length == 3){
            if(args[1].equals("="))return Assignment.def;
            return Assignment.invalid;
        }
        if(args.length == 5){
            if(mapAss.containsKey(args[3])) return mapAss.get(args[3]);
            return Assignment.invalid;
        }
        return Assignment.invalid;
        // a = 5
        // a = b ? c
    }
}
