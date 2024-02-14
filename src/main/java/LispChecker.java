import java.util.Map;
import java.util.Stack;
public class LispChecker {
    Map<Character,Character> map = Map.of(
            ')','(',
            '}','{',
            ']','['
    );
    public boolean isValid(String s) {
        if(s == null || s.isEmpty()) return false;
        Stack<Character> stack = new Stack<>();

        for(int i = 0; i< s.length(); i++) {
            if(map.containsValue(s.charAt(i))) {
                stack.push(s.charAt(i));
            }else {
                if(stack.isEmpty()) return false;
                Character br = stack.pop();
                if(br != map.get(s.charAt(i))) return false;
            }
        }
        if(!stack.isEmpty()) return false;
        else return true;

    }

    public static void main(String[] args) {

        LispChecker solution1 = new LispChecker();
        String s1 = "()"; //true
        String s2 = "()[]{}"; //true
        String s3 = "(]";  //false


        boolean res1 = solution1.isValid(s1);
        boolean res2 = solution1.isValid(s2);
        boolean res3 = solution1.isValid(s3);

        System.out.println("res1 : " + res1);
        System.out.println("res2 : " + res2);
        System.out.println("res3 : " + res3);

    }

}
