public class PrintHandler {
    public static void executePrint(String[] args) throws CompilationError {

        System.out.println("PROGRAM OUTPUT: " + getPrintMessage(args));

    }

    public static int getPrintMessage(String[] args) throws CompilationError {
        String[] expressionArgs = new String[args.length - 1];

        for(int i = 1; i < args.length; i++){
            expressionArgs[i-1] = args[i];
        }

        return ExpressionInteger.executeExpressionInteger(expressionArgs);
    }
}
