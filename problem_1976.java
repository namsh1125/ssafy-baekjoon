import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 도시의 수, 1 <= N <= 200
        int M = Integer.parseInt(br.readLine()); // 여행 계획에 속한 도시들의 수, 1 <= M <= 1000

        boolean[][] isConnected = new boolean[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 1; j <= N; j++) {
                isConnected[i][j] = Integer.parseInt(input[j - 1]) == 1;
            }
        }

        // 자기 자신은 갈 수 있음
        for (int i = 1; i <= N; i++) {
            isConnected[i][i] = true;
        }

        int[] plan = new int[M];
        String[] input = br.readLine().split(" ");
        for (int i = 0; i < M; i++) {
            plan[i] = Integer.parseInt(input[i]);
        }

        // 플로이드 워셜 알고리즘을 사용해서 도시간 연결
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (isConnected[i][k] && isConnected[k][j]) { // 경유해서 갈 수 있다면
                        isConnected[i][j] = true;
                    }
                }
            }
        }

        boolean isAvailable = true;
        for (int i = 1; i < M; i++) {
            int from = plan[i - 1];
            int to = plan[i];

            if (!isConnected[from][to]) {
                isAvailable = false;
                break;
            }
        }

        System.out.println(isAvailable ? "YES" : "NO");
        br.close();
    }

}
