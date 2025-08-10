import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static Set<Integer> inviteList = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            String[] s = br.readLine().split(" ");
            int a = Integer.parseInt(s[0]);
            int b = Integer.parseInt(s[1]);

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        dfs(graph, new boolean[n + 1], 1, 0);

        System.out.println(inviteList.size());
    }

    private static void dfs(Map<Integer, ArrayList<Integer>> graph, boolean[] visited, int node, int depth) {
        if (depth >= 2) { // 친구의 친구까지만 초대
            return ;
        }

        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                inviteList.add(neighbor);
                dfs(graph, visited, neighbor, depth + 1);
            }
        }
    }
}
