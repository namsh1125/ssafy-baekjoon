import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 1 ≤ N ≤ 1000
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = Arrays.copyOf(arr, N);

        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], arr[i] + dp[j]);
                }
            }
        }

        System.out.println(Arrays.stream(dp).max().getAsInt());
        br.close();
    }

}
