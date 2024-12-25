public class AssignmentHandler {
    static void executeAssignment(String[] args) throws Exception {
        String[] expressionArgs = new String[args.length - 2];

        // a = a + 120

        for(int i = 2; i < args.length; i++){
            expressionArgs[i-2] = args[i];
        }

        Runner.ints.setValue(args[0], ExpressionInteger.executeExpressionInteger(expressionArgs));
    }

    public static boolean isNumber(String str){
        for(char ch : str.toCharArray()){
            if(!(ch <= '9' && ch >= '0'))
                return false;
        }
        return true;
    }
}
