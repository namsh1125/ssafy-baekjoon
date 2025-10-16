import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 주전자의 개수, N ≤ 10,000
    private static int K; // 친구들의 수, K ≤ 1,000,000, N ≤ K

    private static long[] capacity; // 막걸리의 용량, 0 ≤ capacity[i] ≤ 2^31 -1

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        capacity = new long[N];
        for (int i = 0; i < N; i++) {
            capacity[i] = Long.parseLong(br.readLine());
        }

        long result = solution();

        System.out.println(result);
        br.close();
    }

    private static long solution() {
        if (K == 1) {
            return Arrays.stream(capacity).min().getAsLong();
        }

        long left = 0;
        long right = Arrays.stream(capacity).max().getAsLong();

        while (left < right) {
            long mid = left + (right - left) / 2;

            long count = 0;
            for (int i = 0; i < N; i++) {
                count += capacity[i] / mid;
            }

            if (count >= K) { // 친구들에게 막걸리를 더 줄 수 있는 경우
                left = mid + 1;

            } else { // 친구들에게 막걸리를 더 줄 수 없는 경우
                right = mid;
            }
        }

        return left - 1;
    }

}
