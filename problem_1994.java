import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 1 <= N <= 2000

        int[] arr = new int[N]; // 0 <= arr[i] <= 1,000,000,000
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        if (N == 1) {
            System.out.println(1);
            return;
        }

        Arrays.sort(arr);

        HashMap<Integer, Integer>[] dp = new HashMap[N]; // dp[i][d] = i번째 원소를 마지막으로 하고 공차 d인 등차수열의 길이
        for (int i = 0; i < N; i++) {
            dp[i] = new HashMap<>();
        }

        int maxLength = 2; // 최소 등차수열의 길이는 2

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                int diff = arr[i] - arr[j];

                // j에서 시작하는 등차수열의 길이 + 1
                int length = dp[j].getOrDefault(diff, 1) + 1;

                // i에서 공차 d인 등차수열의 최대 길이 갱신
                dp[i].put(diff, Math.max(dp[i].getOrDefault(diff, 0), length));

                // 최대 길이 갱신
                maxLength = Math.max(maxLength, dp[i].get(diff));
            }
        }

        System.out.println(maxLength);
    }
}
