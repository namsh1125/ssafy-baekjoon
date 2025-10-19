import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static List<Edge>[] graph;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        graph = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            String[] parts = br.readLine().split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);

            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }

        int result = getSecondTreeDiameter();
        System.out.println(result);

        br.close();
    }

    private static int getSecondTreeDiameter() {
        // 1. 임의의 정점(1)에서 가장 먼 정점(farthestNode1) 찾기
        int[] distancesFromStart = bfs(1);
        int farthestNode1 = 1;
        for (int i = 2; i <= N; i++) {
            if (distancesFromStart[i] > distancesFromStart[farthestNode1]) {
                farthestNode1 = i;
            }
        }

        // 2. farthestNode1(A)에서 가장 먼 정점(farthestNode2, B) 찾기
        int[] distancesFromFarthestNode1 = bfs(farthestNode1); // distA
        int farthestNode2 = farthestNode1;
        for (int i = 1; i <= N; i++) {
            if (distancesFromFarthestNode1[i] > distancesFromFarthestNode1[farthestNode2]) {
                farthestNode2 = i;
            }
        }

        // 3. farthestNode2(B)에서 모든 정점까지의 거리 계산
        int[] distancesFromFarthestNode2 = bfs(farthestNode2); // distB

        // 4. 두 번째 트리 지름 계산

        // 4-1. distA에서 두 번째로 큰 값 찾기
        int max1_A = 0;
        int max2_A = 0;
        for (int i = 1; i <= N; i++) {
            if (i == farthestNode1) continue; // 자기 자신 제외

            if (distancesFromFarthestNode1[i] >= max1_A) {
                max2_A = max1_A;
                max1_A = distancesFromFarthestNode1[i];
            } else if (distancesFromFarthestNode1[i] > max2_A) {
                max2_A = distancesFromFarthestNode1[i];
            }
        }

        // 4-2. distB에서 두 번째로 큰 값 찾기
        int max1_B = 0;
        int max2_B = 0;
        for (int i = 1; i <= N; i++) {
            if (i == farthestNode2) continue; // 자기 자신 제외

            if (distancesFromFarthestNode2[i] >= max1_B) {
                max2_B = max1_B;
                max1_B = distancesFromFarthestNode2[i];
            } else if (distancesFromFarthestNode2[i] > max2_B) {
                max2_B = distancesFromFarthestNode2[i];
            }
        }

        // 5. 둘 중 더 큰 값이 정답
        // (문제에서 "지름과 같을 수 있다"고 했으므로,
        // max1_A는 지름, max2_A는 A에서 시작하는 두번째 긴 경로)
        return Math.max(max2_A, max2_B);
    }

    private static int[] bfs(int start) {
        int[] dist = new int[N + 1];
        boolean[] visited = new boolean[N + 1];

        Arrays.fill(dist, -1);
        dist[start] = 0;
        visited[start] = true;

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (Edge edge : graph[node]) {
                int neighborNode = edge.node;
                if (!visited[neighborNode]) {
                    visited[neighborNode] = true;
                    dist[neighborNode] = dist[node] + edge.weight;
                    queue.add(neighborNode);
                }
            }
        }
        return dist;
    }

    private static class Edge {
        int node;
        int weight;

        public Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }
}
