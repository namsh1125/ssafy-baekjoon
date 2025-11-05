import java.io.*;
import java.util.*;

public class Main {

    private static final int MAX_MATCHES = 100;

    private static final char[] MIN_ONE_DIGIT = new char[8];

    static {
        MIN_ONE_DIGIT[2] = '1';
        MIN_ONE_DIGIT[3] = '7';
        MIN_ONE_DIGIT[4] = '4';
        MIN_ONE_DIGIT[5] = '2'; // 2, 3, 5 중 가장 작은 수
        MIN_ONE_DIGIT[6] = '0'; // 0, 6, 9 중 가장 작은 수, 단, 첫 자리에 올 수 없음
        MIN_ONE_DIGIT[7] = '8';
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 최소 숫자 구하기
        String[] min = new String[MAX_MATCHES + 1];
        Arrays.fill(min, null);

        min[2] = "1";
        min[3] = "7";
        min[4] = "4";
        min[5] = "2";
        min[6] = "6"; // 한 자리로는 6이 가장 작음 (0는 첫 자리에 올 수 없음)
        min[7] = "8";

        for (int i = 8; i <= MAX_MATCHES; i++) {
            String best = null;

            for (int j = 2; j <= 7; j++) {
                if (min[i - j] == null) continue;
                char c = MIN_ONE_DIGIT[j];

                String candidate = min[i - j] + c;
                if (isBetter(candidate, best)) best = candidate;
            }

            min[i] = best;
        }

        // 최대 숫자 구하기
        String[] max = new String[MAX_MATCHES + 1];
        Arrays.fill(max, null);

        for (int i = 2; i <= MAX_MATCHES; i++) {
            StringBuilder value;
            if (i % 2 == 0) {
                value = new StringBuilder("1");
                value.append("1".repeat((i / 2) - 1));

            } else {
                value = new StringBuilder("7");
                value.append("1".repeat((i - 3) / 2));
            }

            max[i] = value.toString();
        }

        int tc = Integer.parseInt(br.readLine()); // 테스트 케이스 수, 1 ≤ tc ≤ 100
        for (int test_case = 1; test_case <= tc; test_case++) {
            int n = Integer.parseInt(br.readLine()); // 성냥개비의 개수, 2 ≤ n ≤ 100
            sb.append(min[n]).append(" ").append(max[n]).append("\n");
        }

        System.out.print(sb.toString().trim());
        br.close();
    }

    private static boolean isBetter(String a, String b) {
        if (b == null) return true;
        if (a.length() != b.length()) return a.length() < b.length();
        return a.compareTo(b) < 0;
    }

}
