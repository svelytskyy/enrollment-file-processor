import java.util.Map;
import java.util.Stack;

/**
 * A class to validate the parentheses of a LISP code.
 */
public class LispChecker {

    // Mapping of closing parentheses to their corresponding opening parentheses
    private final Map<Character, Character> parenthesesMap = Map.of(
            ')', '(',
            '}', '{',
            ']', '['
    );

    /**
     * Validates whether the parentheses in the given string are properly closed and nested.
     *
     * @param s the input string to validate
     * @return true if all parentheses are properly closed and nested, false otherwise
     */
    public boolean isValid(String s) {
        // Return false if input string is null or empty
        if (s == null || s.isEmpty()) return false;

        Stack<Character> stack = new Stack<>();

        // Iterate through each character in the input string
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // If the character is an opening parenthesis, push it onto the stack
            if (parenthesesMap.containsValue(ch)) {
                stack.push(ch);
            } else { // If the character is a closing parenthesis
                // Return false if stack is empty (unmatched closing parenthesis)
                if (stack.isEmpty()) return false;

                // Pop the top character from the stack
                char top = stack.pop();

                // Return false if the corresponding opening parenthesis doesn't match
                if (top != parenthesesMap.get(ch)) return false;
            }
        }

        // Return true if the stack is empty (all parentheses matched and closed)
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        // Test cases
        LispChecker solution1 = new LispChecker();
        String s1 = "()";     // true
        String s2 = "()[]{}"; // true
        String s3 = "(]";     // false

        // Perform tests and print results
        boolean res1 = solution1.isValid(s1);
        boolean res2 = solution1.isValid(s2);
        boolean res3 = solution1.isValid(s3);

        System.out.println("res1 : " + res1);
        System.out.println("res2 : " + res2);
        System.out.println("res3 : " + res3);
    }
}
