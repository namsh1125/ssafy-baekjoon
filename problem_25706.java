import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] height = new int[N]; // 점프대의 높이
        for (int i = 0; i < N; i++) {
            height[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N];
        dp[N - 1] = 1;

        for (int i = N - 2; i >= 0; i--) {
            if (i + height[i] + 1 >= N) dp[i] = 1;
            else dp[i] = dp[i + height[i] + 1] + 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(dp[i]).append(" ");
        }

        System.out.print(sb.toString().trim());
        br.close();
    }

}
