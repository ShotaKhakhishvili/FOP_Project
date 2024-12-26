public class DeclarationHandler {

    public static void executeDeclaration(String[] args){

        Runner.intStack.peek().declareVariable(args[1], null);

    }

}
