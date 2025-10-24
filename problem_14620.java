import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 화단의 한 변의 길이, 6 ≤ N ≤ 10
    private static int[][] costs;

    private static int minCost;
    private static boolean[][] isEmpty;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        costs = new int[N][N];
        isEmpty = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                costs[i][j] = Integer.parseInt(st.nextToken());
                isEmpty[i][j] = true;
            }
        }

        minCost = Integer.MAX_VALUE;
        dfs(0, 0);

        System.out.println(minCost);
        br.close();
    }

    private static void dfs(int totalCost, int depth) {
        if (depth >= 3) {
            minCost = Math.min(minCost, totalCost);
            return;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (!isAvailable(i, j)) continue;

                int cost = costs[i][j - 1] + costs[i - 1][j] + costs[i][j] + costs[i][j + 1] + costs[i + 1][j];
                isEmpty[i][j - 1] = false;
                isEmpty[i - 1][j] = false;
                isEmpty[i][j] = false;
                isEmpty[i][j + 1] = false;
                isEmpty[i + 1][j] = false;

                dfs(totalCost + cost, depth + 1);

                isEmpty[i][j - 1] = true;
                isEmpty[i - 1][j] = true;
                isEmpty[i][j] = true;
                isEmpty[i][j + 1] = true;
                isEmpty[i + 1][j] = true;
            }
        }
    }

    private static boolean isAvailable(int i, int j) {
        if (!(1 <= i && i < N - 1 && 1 <= j && j < N - 1)) return false; // 범위를 넘어가는 경우
        return isEmpty[i][j - 1] && isEmpty[i - 1][j] && isEmpty[i][j] && isEmpty[i][j + 1] && isEmpty[i + 1][j];
    }

}
