import java.io.*;
import java.util.*;

public class Main {

    private static final int EMPTY = 0;
    private static final int WALL = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 집의 크기

        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(input[j]);
            }
        }

        long[][][] dp = new long[N][N][3];
        dp[0][1][0] = 1; // (0,1)에 가로로 파이프를 놓는 경우의 수는 1가지

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == 0 && j == 0 || i == 0 && j == 1) continue; // 시작점
                if (map[i][j] == WALL) continue; // 벽인 경우

                if (j >= 1 && map[i][j - 1] != WALL) { // map[i][j - 1]에서 가로 파이프를 설치하여 올 수 있는 경우
                    dp[i][j][0] = dp[i][j - 1][0] + dp[i][j - 1][2];
                }

                if (i >= 1 && map[i - 1][j] != WALL) { // map[i - 1][j]에서 세로 파이프를 설치하여 올 수 있는 경우
                    dp[i][j][1] = dp[i - 1][j][1] + dp[i - 1][j][2];
                }

                if (i >= 1 && j >= 1 && map[i - 1][j] != WALL && map[i][j - 1] != WALL) { // map[i - 1][j - 1]에서 대각선 파이프를 설치하여 올 수 있는 경우
                    dp[i][j][2] = dp[i - 1][j - 1][0] + dp[i - 1][j - 1][1] + dp[i - 1][j - 1][2];
                }
            }
        }

        System.out.println(dp[N - 1][N - 1][0] + dp[N - 1][N - 1][1] + dp[N - 1][N - 1][2]);
        br.close();
    }

}
