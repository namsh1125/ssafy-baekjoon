import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static final int INF = 123_456_789;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken()); // 늘리고자 하는 고객 수, 1 ≤ C ≤ 1000
        int N = Integer.parseInt(st.nextToken()); // 도시의 수, 1 ≤ N ≤ 20

        int[] dp = new int[C + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0; // 고객 수 0명을 늘리기 위한 비용은 0원

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            int cost = Integer.parseInt(st.nextToken()); // 도시 i에서 광고를 하는 데 드는 비용, 1 ≤ cost ≤ 100
            int customers = Integer.parseInt(st.nextToken()); // 도시 i에서 광고를 통해 늘어나는 고객 수, 1 ≤ customers ≤ 100

            for (int j = 1; j <= C; j++) {
                if (j - customers >= 0) {
                    dp[j] = Math.min(dp[j], dp[j - customers] + cost);
                } else {
                    dp[j] = Math.min(dp[j], cost);
                }
            }
        }

        System.out.println(dp[C]);
        br.close();
    }

}
