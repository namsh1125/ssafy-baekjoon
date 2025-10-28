import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 1 ≤ N ≤ 5 × 10^5
    private static long[] A; // 잉크지수, 1 ≤ Aᵢ ≤ 10^18
    private static long[] B; // 점도지수, 오름차순, 1 ≤ Bᵢ ≤ 10^18, Aᵢ ≥ Bᵢ

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        A = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Long.parseLong(st.nextToken());
        }

        B = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            B[i] = Long.parseLong(st.nextToken());
        }

        int[] answer = new int[N];
        for (int i = 0; i < N; i++) {
            int left = i + 1, right = N - 1;
            long ink = A[i];

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (ink < B[mid]) { // 칠할 수 없는 경우
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            answer[i] = left - i - 1;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(answer[i]).append(" ");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

}
