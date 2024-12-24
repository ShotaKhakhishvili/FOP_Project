public class DeclarationHandler {
    public static void executeDeclaration(String[] args){
      if(args.length != 4 || !args[2].equals("as") || !args[3].equals("integer"))
          throw new RuntimeException("Invalid Declaration on line " + (Runner.pc + 1));

      Runner.ints.declareVariable(args[1], null);
    }
}
