import java.io.*;
import java.util.*;

public class Main {

    private static final int MOD = 1000000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] cipher = br.readLine().toCharArray();
        int length = cipher.length;

        int[] dp = new int[length + 1];
        dp[0] = 1; // 아무 글자도 없을 때는 1가지 방법
        dp[1] = cipher[0] == '0' ? 0 : 1; // 첫 글자가 0이면 해석 불가, 아니면 1가지 방법
        for (int i = 2; i <= length; i++) {
            int oneDigit = cipher[i - 1] - '0'; // 한 자리 숫자
            int twoDigits = (cipher[i - 2] - '0') * 10 + oneDigit; // 두 자리 숫자

            // 한 자리 숫자가 1~9 사이면 이전 방법 수를 더함
            if (oneDigit >= 1 && oneDigit <= 9) {
                dp[i] = (dp[i] + dp[i - 1]) % MOD;
            }

            // 두 자리 숫자가 10~26 사이면 이전 두 방법 수를 더함
            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] = (dp[i] + dp[i - 2]) % MOD;
            }
        }

        System.out.println(dp[length]);
        br.close();
    }

}
