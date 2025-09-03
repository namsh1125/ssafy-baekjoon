import java.io.*;
import java.util.*;

public class Main {

    private static final int INF = 999_999_999;

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 입력 받기
        int[][] cost = new int[N][3];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp 배열 초기화
        int[][] dp = new int[N + 1][3]; // i: i번째 집 / j: 0 -> RED로 칠할 때 비용 / 1 -> Green으로 칠할 때 비용 / 2 -> Blue로 칠할 때 비용
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1) dp[1][j] = cost[0][j]; // 1번째 집은 아무 색상이나 색칠 가능
                else dp[i][j] = INF;
            }
        }

        // 2 ~ N - 1번째 집 최소 비용 초기화
        for (int i = 2; i <= N; i++) {
            // i (2 ≤ i ≤ N - 1)번 집의 색은 i - 1번, i + 1번 집의 색과 같지 않아야 한다.
            dp[i][RED] = Math.min(dp[i][RED], dp[i - 1][BLUE] + cost[i - 1][RED]);
            dp[i][RED] = Math.min(dp[i][RED], dp[i - 1][GREEN] + cost[i - 1][RED]);

            dp[i][GREEN] = Math.min(dp[i][GREEN], dp[i - 1][RED] + cost[i - 1][GREEN]);
            dp[i][GREEN] = Math.min(dp[i][GREEN], dp[i - 1][BLUE] + cost[i - 1][GREEN]);

            dp[i][BLUE] = Math.min(dp[i][BLUE], dp[i - 1][RED] + cost[i - 1][BLUE]);
            dp[i][BLUE] = Math.min(dp[i][BLUE], dp[i - 1][GREEN] + cost[i - 1][BLUE]);
        }

        int minCost = Arrays.stream(dp[N]).min().getAsInt();
        System.out.println(minCost);

        br.close();
    }

}
