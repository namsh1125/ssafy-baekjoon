import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]); // 물품의 수, 1 ≤ N ≤ 100
        int K = Integer.parseInt(input[1]); // 최대 무게, 1 ≤ K ≤ 100,000

        int[][] dp = new int[N + 1][K + 1]; // dp[i][j]: i번째 물품까지 고려했을 때, j 무게 이하로 담을 수 있는 최대 가치
        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            int weight = Integer.parseInt(input[0]);
            int value = Integer.parseInt(input[1]);

            for (int j = 0; j <= K; j++) {
                if (j < weight) {
                    dp[i][j] = dp[i - 1][j]; // 물품을 담을 수 없는 경우
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight] + value); // 물품을 담는 경우와 담지 않는 경우 중 최대값
                }
            }
        }

        System.out.println(dp[N][K]);
        br.close();
    }

}
