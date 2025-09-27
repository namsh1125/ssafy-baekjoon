import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int[] values; // 각 동굴을 탐사해서 얻을 수 있는 보물의 가치, 0 ≤ vi ≤ 10^4
    private static List<int[]>[] graph;
    private static int[] dp;  // 각 노드에서 얻을 수 있는 최대 이익
    private static int[] next;
    private static boolean[] visited;  // 메모이제이션용

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 탐사할 수 있는 동굴의 수, 1 ≤ N ≤ 2 * 10^4
            int E = Integer.parseInt(st.nextToken()); // 서로 직접 연결된 동굴 쌍의 수, 0 ≤ E ≤ 10^5

            // 초기화
            values = new int[N + 1];
            graph = new ArrayList[N + 1];
            visited = new boolean[N + 1];
            dp = new int[N + 1];
            next = new int[N + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                values[i] = Integer.parseInt(st.nextToken());
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()); // 1 ≤ a ≤ N
                int b = Integer.parseInt(st.nextToken()); // 1 ≤ b ≤ N
                int c = Integer.parseInt(st.nextToken()); // 0 ≤ c ≤ 10^4

                graph[a].add(new int[]{b, c});
            }

            dfs(1); // DFS로 최대 이익 계산

            // 경로 길이 계산
            int pathLen = 0;
            int curr = 1;
            while (curr != 0) {
                pathLen++;
                curr = next[curr];
            }

            // 결과 출력
            sb.append(dp[1]).append(' ').append(pathLen).append('\n');

            // 경로 출력
            curr = 1;
            boolean first = true;
            while (curr != 0) {
                if (!first) sb.append(' ');
                sb.append(curr);
                first = false;
                curr = next[curr];
            }
            sb.append('\n');
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static void dfs(int node) {
        if (visited[node]) {
            return;
        }

        dp[node] = values[node];
        next[node] = 0;
        visited[node] = true;

        for (int[] edge : graph[node]) {
            int child = edge[0];
            int cost = edge[1];

            dfs(child);

            int profit = dp[child] - cost;
            if (profit > 0) {
                int total = values[node] + profit;
                if (total > dp[node]) {
                    dp[node] = total;
                    next[node] = child;
                }
            }
        }
    }

}
