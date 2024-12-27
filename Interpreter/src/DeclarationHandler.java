public class DeclarationHandler {

    /**
     * Executes a declaration operation by declaring a new variable in the current scope.
     *
     * @param args The arguments for the declaration. The second argument (args[1]) is the variable name.
     */
    public static void executeDeclaration(String[] args) {
        // Declares a variable in the current scope with a default value of null
        Runner.intStack.peek().declareVariable(args[1], null);
    }

}