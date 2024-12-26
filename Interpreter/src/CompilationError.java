public class CompilationError extends Exception {
    public CompilationError(String message) {
        super(message + " on line " + (Runner.pc + 1));
    }

}
