public class PrintHandler {
    public static void executePrint(String[] args, boolean isString) throws CompilationError {

        System.out.println("PROGRAM OUTPUT: " + getPrintMessage(args, isString));

    }

    public static String getPrintMessage(String[] args, boolean isString) throws CompilationError {
        if(isString)
            return getStringPrintMessage();

        String[] expressionArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i];
        }

        return Integer.toString(ExpressionInteger.executeExpressionInteger(expressionArgs));
    }

    private static String getStringPrintMessage() throws CompilationError {
        StringBuilder current = new StringBuilder();
        boolean string = false;
        StringBuilder answer = new StringBuilder();

        String stringLine = Runner.lines[Runner.pc];
        char[] line = stringLine.toCharArray();

        // Skipping to the prin_t_ part this way, since we know the first word is _print_
        int i = 0;
        while (line[i] != 't')i++;
        for(i++; i < line.length; i++){
            if(string){
                if(line[i] == '"'){
                    answer.append(current);
                    current = new StringBuilder();
                    string = false;
                    continue;
                }
                current.append(line[i]);
                continue;
            }
            if(line[i] == ' ' || line[i] == '"'){
                if(!current.isEmpty()){
                    String val = String.valueOf(Variables.getIntegerValue(String.valueOf(current)));
                    answer.append(val);
                }
                current = new StringBuilder();
                if(line[i] == '"')
                    string = true;
                continue;
            }
            if(InstructionHandler.invalidChars.contains(line[i])){
                throw new CompilationError("Invalid Print Statement");
            }
            current.append(line[i]);
        }

        return answer.toString();
    }
}
