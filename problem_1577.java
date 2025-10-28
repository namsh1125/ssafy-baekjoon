import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 도로의 가로 크기, 1 ≤ N ≤ 100
        int M = Integer.parseInt(st.nextToken()); // 도로의 세로 크기, 1 ≤ M ≤ 100
        int K = Integer.parseInt(br.readLine()); // 공사중인 도로의 개수, 0 ≤ K ≤ 50

        Set<String> isImpossible = new HashSet<>();
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            isImpossible.add(b + "/" + a + "/" + d + "/" + c);
            isImpossible.add(d + "/" + c + "/" + b + "/" + a);
        }

        long[][] count = new long[M + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            String key = 0 + "/" + (i - 1) + "/" + "0" + "/" + i;
            if (isImpossible.contains(key)) break;
            count[0][i] = 1;
        }

        for (int i = 1; i <= M; i++) {
            String key = (i - 1) + "/" + 0 + "/" + i + "/" + 0;
            if (isImpossible.contains(key)) break;
            count[i][0] = 1;
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                String key = (i - 1) + "/" + j + "/" + i + "/" + j;
                if (!isImpossible.contains(key)) { // (i-1, j)에서 (i, j)로 올 수 있는 경우
                    count[i][j] += count[i - 1][j];
                }

                key = i + "/" + (j - 1) + "/" + i + "/" + j;
                if (!isImpossible.contains(key)) { // (i, j-1)에서 (i, j)로 올 수 있는 경우
                    count[i][j] += count[i][j - 1];
                }
            }
        }

        System.out.println(count[M][N]);
        br.close();
    }

}
