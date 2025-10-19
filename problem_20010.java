import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 마을의 수, 1 ≤ N ≤ 1,000
    private static int K; // 설치 가능한 교역로의 수, 1 ≤ K ≤ 1,000,000
    private static List<Edge>[] graph;

    private static int[] parents;
    private static int[] ranks;

    private static PriorityQueue<Edge> minQueue = new PriorityQueue<>();

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        initUnionFind();

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            minQueue.offer(new Edge(from, to, weight));
        }

        int minCost = createSpanningTree();
        int maxDistance = getMaxDistance();

        System.out.println(minCost);
        System.out.println(maxDistance);
        br.close();
    }

    private static int createSpanningTree() {
        int totalCost = 0;
        int unionCount = 0;

        while (!minQueue.isEmpty() || unionCount < N - 1) {
            Edge edge = minQueue.poll();
            int from = edge.from;
            int to = edge.to;
            int weight = edge.weight;

            if (find(from) != find(to)) {
                union(from, to);

                // 스패닝 트리 생성
                graph[from].add(new Edge(from, to, weight));
                graph[to].add(new Edge(to, from, weight));

                totalCost += weight;
                unionCount++;
            }
        }

        return totalCost;
    }

    private static int getMaxDistance() {
        // 임의의 점 0에서 모든 점까지의 최단 거리 구하기
        int[] distances = dijkstra(0);

        // 임의의 점 0에서 가장 먼 점 찾기
        int farthestNode = 0;
        for (int i = 1; i < N; i++) {
            if (distances[i] > distances[farthestNode]) {
                farthestNode = i;
            }
        }

        // 가장 먼 점에서 모든 점까지의 최단 거리 구하기
        distances = dijkstra(farthestNode);

        return Arrays.stream(distances).max().getAsInt();
    }

    private static int[] dijkstra(int start) {
        int[] distances = new int[N];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        for (Edge edge : graph[start]) {
            queue.offer(edge);
        }

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            int to = edge.to;
            int weight = edge.weight;

            if (distances[to] > distances[edge.from] + weight) {
                distances[to] = distances[edge.from] + weight;

                for (Edge nextEdge : graph[to]) {
                    queue.offer(nextEdge);
                }
            }
        }

        return distances;
    }

    private static void union(int node1, int node2) {
        int parent1 = find(node1);
        int parent2 = find(node2);

        if (parent1 == parent2) return;
        if (ranks[parent1] < ranks[parent2]) {
            parents[parent1] = parent2;

        } else if (ranks[parent1] > ranks[parent2]) {
            parents[parent2] = parent1;

        } else {
            parents[parent2] = parent1;
            ranks[parent1]++;
        }
    }

    private static int find(int node) {
        if (node != parents[node]) parents[node] = find(parents[node]);
        return parents[node];
    }

    private static void initUnionFind() {
        parents = new int[N];
        ranks = new int[N];

        for (int i = 0; i < N; i++) {
            parents[i] = i;
            ranks[i] = 0;
        }
    }

    private static class Edge implements Comparable<Edge> {
        int from, to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

}
