import java.io.*;
import java.util.*;

public class Main {

    private static final int DEVIL = 0;
    private static final int ANGEL = 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] str = br.readLine().toCharArray(); // 마법의 두루마리에 적힌 문자열, 1 <= str.length() <= 100
        int strLength = str.length;

        char[] devil = br.readLine().toCharArray(); // 악마의 돌다리
        char[] angel = br.readLine().toCharArray(); // 천사의 돌다리
        int bridgeLength = devil.length;

        long[][][] dp = new long[bridgeLength][2][str.length]; // dp[i][j][k]: i번째 돌다리에 j(0: 악마, 1: 천사)의 돌다리에서 마법의 두루마리 k번째 문자열까지 올 수 있는 횟수

        // 초기값 채우기
        for (int i = 0; i < bridgeLength; i++) {
            if (devil[i] == str[0]) dp[i][DEVIL][0] = 1;
            if (angel[i] == str[0]) dp[i][ANGEL][0] = 1;
        }

        for (int i = 1; i < strLength; i++) {
            for (int j = 0; j < bridgeLength; j++) {
                if (devil[j] == str[i]) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (angel[k] == str[i - 1]) {
                            dp[j][DEVIL][i] += dp[k][ANGEL][i - 1];
                        }
                    }
                }

                if (angel[j] == str[i]) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (devil[k] == str[i - 1]) {
                            dp[j][ANGEL][i] += dp[k][DEVIL][i - 1];
                        }
                    }
                }
            }
        }

        long answer = 0;
        for (int i = 0; i < bridgeLength; i++) {
            answer += dp[i][DEVIL][strLength - 1];
            answer += dp[i][ANGEL][strLength - 1];
        }

        System.out.println(answer);
        br.close();
    }

}
