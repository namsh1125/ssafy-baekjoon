import java.io.*;
import java.util.*;

public class Main {

    private static char[] string;
    private static String bombString;
    private static int length;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        string = br.readLine().toCharArray();
        bombString = br.readLine();
        length = bombString.length();

        Stack<Character> stack = new Stack<>();
        for (char c : string) {
            stack.add(c);

            while (true) {
                if (c != bombString.charAt(length - 1)) break;
                if (!boom(stack)) break;
            }
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        String result = sb.reverse().toString();

        System.out.println(result.isEmpty() ? "FRULA" : result);
        br.close();
    }

    private static boolean boom(Stack<Character> stack) {
        if (stack.size() < length) return false;

        StringBuilder sb = new StringBuilder();

        Stack<Character> temp = new Stack<>();
        for (int i = 0; i < length; i++) {
            char c = stack.pop();
            sb.append(c);
            temp.push(c);
        }

        if (sb.reverse().toString().equals(bombString)) return true;

        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }

        return false;
    }

}
