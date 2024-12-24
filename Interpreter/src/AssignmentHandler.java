public class AssignmentHandler {
    static void executeAssignment(String[] args, Assignment assignment){
        // Checking if the destination variable is declared
        if(!Runner.ints.containsElement(args[0]))
            throw new RuntimeException("Variable '" + args[0] + "' is not declared on line " + (Runner.pc + 1));

        // Checking the validity of the left operand
        if(!(Runner.ints.containsElement(args[2]) || isNumber(args[2])))
            throw new RuntimeException("Assignment operation was invalid. '" + args[2] + "' was the problem on line " + (Runner.pc + 1));

        int first; // left operand
        if(isNumber(args[2]))
            first = Integer.parseInt(args[2]);
        else
            first = Runner.ints.getValue(args[2]);

        if(assignment == Assignment.def){
            Runner.ints.setValue(args[0], first);
            return;
        }

        // Checking the validity of the left operand
        if(!(Runner.ints.containsElement(args[4]) || isNumber(args[4])))
            throw new RuntimeException("Assignment operation was invalid. '" + args[4] + "' was the problem on line " + (Runner.pc + 1));

        int second; // right operand

        if(isNumber(args[4]))
            second = Integer.parseInt(args[4]);
        else
            second = Runner.ints.getValue(args[4]);

        int value = 0;
        switch (assignment){
            case add :
                value = first + second;
                break;
            case sub :
                value = first - second;
                break;
            case mult :
                value = first * second;
                break;
            case div :
                value = first / second;
                break;
            case mod :
                value = first % second;
        }

        Runner.ints.setValue(args[0], value);
    }

    public static boolean isNumber(String str){
        for(char ch : str.toCharArray()){
            if(!(ch <= '9' && ch >= '0'))
                return false;
        }
        return true;
    }
}
