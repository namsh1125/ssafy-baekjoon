import java.io.*;
import java.util.*;

public class Main {

    private static final int SPACE = 0;
    private static final int LINE = 1;

    private static int N;
    private static int length;
    private static boolean[] isLine;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        while (true) {
            String str = br.readLine();
            if (str == null) break;
            if (str.isBlank()) break;

            N = Integer.parseInt(str);

            length = (int) Math.pow(3, N);
            isLine = new boolean[length];

            print(0, length, 0);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < length; i++) {
                if (isLine[i]) result.append("-");
                else result.append(" ");
            }

            sb.append(result).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static void print(int start, int length, int depth) {
        if (depth == N) {
            isLine[start] = true;
            return;
        }

        print(start, length / 3, depth + 1);
        print(start + 2 * (length / 3), length / 3, depth + 1);
    }

}
