import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 도시의 개수, 1 ≤ N ≤ 1,000
    private static int M; // 버스의 개수, 1 ≤ M ≤ 100,000

    private static List<Edge>[] graph;
    private static int INF = 999_999_999;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        graph = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            long cost = Long.parseLong(st.nextToken()); // 0 ≤ cost ≤ 100,000

            graph[start].add(new Edge(end, cost));
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        long result = dijkstra(start, end);

        System.out.println(result);
        br.close();
    }

    private static long dijkstra(int start, int end) {
        long[] distance = new long[N + 1];
        Arrays.fill(distance, INF);
        distance[start] = 0;

        boolean[] visited = new boolean[N + 1];

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(start, 0));

        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            if (visited[current.node]) continue;
            visited[current.node] = true;

            for (Edge edge : graph[current.node]) {
                if (distance[current.node] + edge.weight < distance[edge.node]) {
                    distance[edge.node] = distance[current.node] + edge.weight;
                    queue.add(new Edge(edge.node, distance[edge.node]));
                }
            }
        }

        return distance[end];
    }

    private static class Edge implements Comparable<Edge> {
        int node;
        long weight;

        public Edge(int node, long weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.weight, o.weight);
        }
    }

}
