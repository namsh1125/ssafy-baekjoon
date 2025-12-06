import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException { // 시간제한: 1초, 메모리 제한: 1024MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 햄버거의 수, 1 ≤ N ≤ 50

        int[] burgers = new int[N];
        int sum = 0;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            burgers[i] = Integer.parseInt(st.nextToken()); // 각 햄버거의 효용, 1 ≤ 효용 ≤ 50
            sum += burgers[i];
        }

        boolean[][] dp = new boolean[sum + 1][sum + 1]; // dp[i][j]: 첫 번째 사람이 i의 효용, 두 번째 사람이 j의 효용을 가질 수 있는지 여부
        dp[0][0] = true;

        int currentSum = 0;
        for (int burger : burgers) {
            for (int i = currentSum; i >= 0; i--) {
                for (int j = currentSum; j >= 0; j--) {
                    if (dp[i][j]) {
                        dp[i + burger][j] = true; // 첫 번째 사람이 햄버거를 선택하는 경우
                        dp[i][j + burger] = true; // 두 번째 사람이 햄버거를 선택하는 경우
                    }
                }
            }

            currentSum += burger;
        }

        int maxGilwon = 0;
        for (int i = 0; i <= sum; i++) {
            for (int j = 0; j <= sum; j++) {
                if (dp[i][j]) {
                    int k = sum - i - j; // 길원이의 효용

                    if (i >= j && j >= k) { // 서열과 규칙에 따라 효용이 올바르게 배분된 경우
                        maxGilwon = Math.max(maxGilwon, k);
                    }
                }
            }
        }

        System.out.println(maxGilwon);
        br.close();
    }
}
