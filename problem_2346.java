import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        int[] ballons = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            ballons[i] = Integer.parseInt(st.nextToken());
        }

        // 원형 이중 연결 리스트 (배열 버전)
        int[] left = new int[N];
        int[] right = new int[N];
        for (int i = 0; i < N; i++) {
            left[i] = (i - 1 + N) % N;
            right[i] = (i + 1) % N;
        }

        StringBuilder sb = new StringBuilder();
        int cur = 0;
        int remaining = N;

        while (true) {
            sb.append(cur + 1).append(' ');
            int k = ballons[cur];

            // cur 제거: 좌우 연결
            int L = left[cur];
            int R = right[cur];
            right[L] = R;
            left[R] = L;
            remaining--;

            if (remaining == 0) break;

            if (k > 0) {
                int steps = (k - 1) % remaining; // k > 0이면 오른쪽으로 k - 1칸
                cur = R;
                for (int i = 0; i < steps; i++) {
                    cur = right[cur];
                }

            } else { // k < 0
                int steps = (-k - 1) % remaining; // k < 0이면 왼쪽으로 |k| - 1칸
                cur = L;
                for (int i = 0; i < steps; i++) {
                    cur = left[cur];
                }
            }
        }

        System.out.println(sb.toString().trim());
        br.close();
    }
}
