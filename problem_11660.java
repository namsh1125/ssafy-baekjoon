import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");

        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);

        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            s = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(s[j]);
            }
        }

        int[][] prefixSum = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                prefixSum[i][j] = prefixSum[i - 1][j] + prefixSum[i][j - 1] + arr[i - 1][j - 1] - prefixSum[i -1][j - 1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            s = br.readLine().split(" ");
            int i1 = Integer.parseInt(s[0]);
            int j1 = Integer.parseInt(s[1]);
            int i2 = Integer.parseInt(s[2]);
            int j2 = Integer.parseInt(s[3]);

            // (x1, y1) ~ (x2, y2) 영역의 합 구하기
            int sum = prefixSum[i2][j2] - prefixSum[i2][j1 - 1] - prefixSum[i1 - 1][j2] + prefixSum[i1 - 1][j1 - 1];
            sb.append(sum);

            if (i != m - 1) sb.append("\n");
        }

        System.out.print(sb);

    }

}
