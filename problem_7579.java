import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]); // 활성화 된 앱의 수, 1 ≤ N ≤ 100
        int M = Integer.parseInt(input[1]); // 추가로 확보해야 하는 메모리 크기, 1 ≤ M ≤ 10,000,000

        int[] m = new int[N]; // 각 앱 Ai가 사용하는 메모리 크기 mi, 1 ≤ m1, ..., mN ≤ 10,000,000
        int[] c = new int[N]; // 각 앱 Ai를 비활성화한 후에 다시 실행하고자 할 경우, 추가적으로 들어가는 비용 ci, 0 ≤ c1, ..., cN ≤ 100

        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            m[i] = Integer.parseInt(input[i]);
        }

        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            c[i] = Integer.parseInt(input[i]);
        }

        int maxCost = Arrays.stream(c).sum();
        int[] dp = new int[maxCost + 1]; // dp[i]: i 비용으로 확보할 수 있는 최대 메모리 크기
        for (int i = 0; i < N; i++) {
            for (int cost = maxCost; cost >= c[i]; cost--) {
                dp[cost] = Math.max(dp[cost], dp[cost - c[i]] + m[i]);
            }
        }

        int answer = 0;
        for (int cost = 0; cost <= maxCost; cost++) {
            if (dp[cost] >= M) { // M 이상의 메모리 확보 가능
                answer = cost;
                break;
            }
        }

        System.out.println(answer);
        br.close();
    }

}
