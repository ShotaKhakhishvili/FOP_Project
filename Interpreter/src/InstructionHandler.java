import java.util.*;
import java.util.stream.Collectors;

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
    Map<String,Object> mapForSpaces = new HashMap<>() {{
        put("dim", Instruction.declaration);
        put("if", Instruction.iif);
        put("while", Instruction.wwhile);
        put("print", Instruction.print);
        put("input", Instruction.input);
        put("wend", Instruction.wend);
        put("+", Assignment.add);
        put("-", Assignment.sub);
        put("*", Assignment.mult);
        put("/", Assignment.div);
        put("%", Assignment.mod);
        put("=", 404);
    }};

    static Set<Character> invalidChars = new HashSet<>(Set.of(
            '@', '#', '%', '^', '&', '*', '(', ')', '-', '+', '=', '{', '}',
            '[', ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?',
            '/', '~', '`', ' '
    ));

    public static String[] split(String args) throws CompilationError {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < args.length()) {
            char c = args.charAt(i);

            // Skip spaces
            if (c == ' ') {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                i++;
                continue;
            }

            if (invalidChars.contains(c)) {
                // Handle multi-character operators like '<>'
                if (c == '<' && i + 1 < args.length() && args.charAt(i + 1) == '>') {
                    if (sb.length() > 0) {
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
                    tokens.add("<>");
                    i += 2;
                    continue;
                }

                // Handle single-character operators
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }

                // Check if the operator is supported
                String operator = String.valueOf(c);
                if (!isSupportedOperator(operator) && c != '"') {
                    throw new CompilationError("Illegal character encountered: " + c);
                }

                tokens.add(operator);
                i++;
                continue;
            }

            // Accumulate alphanumeric characters (identifiers, literals, operators like 'and', 'or')
            sb.append(c);
            i++;
        }

        // Add any remaining token
        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }

        return tokens.toArray(new String[0]);
    }

    /**
     * Checks if the opertor is suppored in the current context.
     *
     * @param operator The operator string.
     * @return True if supported, false otherwise.
     */
    private static boolean isSupportedOperator(String operator) {
        // Define all supported single-character operators
        Set<String> supportedOperators = new HashSet<>(Set.of(
                "(", ")", "=", "<", ">", "<>", "+", "-", "*", "/", "%"
        ));
        return supportedOperators.contains(operator);
    }

    static Instruction decode(String[] args){
        if(args.length == 0)return Instruction.empty;

        if(map.containsKey(args[0]))
            return map.get(args[0]);

        if(args.length == 1)return Instruction.invalid;

        if(args[0].equals("end") && args[1].equals("if")) {
            if(args.length == 2) return Instruction.endif;
            return Instruction.invalid;
        }

        if(args[1].equals("="))
            return Instruction.assignment;

        return Instruction.invalid;
    }

}
