public class PrintHandler {
    public static void executePrint(String[] args){
        if(args.length > 2)
            throw new RuntimeException("Invalid print command on line " + (Runner.pc+1));

        if(AssignmentHandler.isNumber(args[1])){
            print(Integer.parseInt(args[1]));
        }
        else{
            if(!Runner.ints.containsElement(args[1]))
                throw new RuntimeException("Variable '" + args[1] + "' is not declared on line " + (Runner.pc + 1));
            else
                print(Runner.ints.getValue(args[1]));
        }
    }

    private static void print(Integer str){
        System.out.println("PROGRAM OUTPUT: " + str);
    }
}
