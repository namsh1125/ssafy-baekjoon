import java.io.*;
import java.util.*;

public class Main {

    private static int V; // 정점의 개수, 3 ≤ V ≤ 10,000
    private static int E; // 도로의 개수, 0 ≤ E ≤ 300,000

    private static Map<Integer, ArrayList<Edge>> graph;

    private static int INF = 999_999_999;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        V = Integer.parseInt(input[0]); // 정점의 개수, 3 ≤ V ≤ 10,000
        E = Integer.parseInt(input[1]); // 도로의 개수, 0 ≤ E ≤ 300,000

        graph = new HashMap<>();
        for (int i = 1; i <= V; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            input = br.readLine().split(" ");
            int u = Integer.parseInt(input[0]);
            int v = Integer.parseInt(input[1]);
            int w = Integer.parseInt(input[2]);

            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        input = br.readLine().split(" ");
        int M = Integer.parseInt(input[0]); // 맥도날드의 수, 1 ≤ M ≤ V - 2
        int x = Integer.parseInt(input[1]); // 맥세권일 조건, 1 ≤ x ≤ 100,000,000
        int[] mcdonalds = new int[M];

        input = br.readLine().split(" ");
        for (int i = 0; i < M; i++) {
            mcdonalds[i] = Integer.parseInt(input[i]);
        }

        input = br.readLine().split(" ");
        int S = Integer.parseInt(input[0]); // 스타벅스의 수, 1 ≤ S ≤ V - 2
        int y = Integer.parseInt(input[1]); // 스세권일 조건, 1 ≤ y ≤ 100,000,000
        int[] starbucks = new int[S];

        input = br.readLine().split(" ");
        for (int i = 0; i < S; i++) {
            starbucks[i] = Integer.parseInt(input[i]);
        }

        // 맥도날드 집합이라는 V + 1번째 가상 노드 추가
        graph.put(V + 1, new ArrayList<>());
        for (int mcdonald : mcdonalds) {
            graph.get(V + 1).add(new Edge(mcdonald, 0));
        }

        int[] mDist = dijkstra();

        // 맥도날드 가상 노드 제거
        graph.remove(V + 1);

        // 스타벅스 집합이라는 V + 1번째 가상 노드 추가
        graph.put(V + 1, new ArrayList<>());
        for (int starbuck : starbucks) {
            graph.get(V + 1).add(new Edge(starbuck, 0));
        }

        int[] sDist = dijkstra();

        // 스타벅스 가상 노드 제거
        graph.remove(V + 1);

        boolean[] isStore = new boolean[V + 1];
        for (int i = 0; i < M; i++) {
            isStore[mcdonalds[i]] = true;
        }

        for (int i = 0; i < S; i++) {
            isStore[starbucks[i]] = true;
        }

        int answer = Integer.MAX_VALUE;
        for (int i = 1; i <= V; i++) {
            if (isStore[i]) continue;
            if (mDist[i] <= x && sDist[i] <= y) {
                answer = Math.min(answer, mDist[i] + sDist[i]);
            }
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
        br.close();
    }

    private static int[] dijkstra() {
        int[] dist = new int[V + 2];
        Arrays.fill(dist, INF);

        // 거리 초기화
        dist[V + 1] = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(V + 1, 0));

        while (!queue.isEmpty()) {
            Edge current = queue.poll();

            int currentNode = current.node;
            int currentWeight = current.weight;

            // 이미 처리된 노드면 스킵
            if (dist[currentNode] < currentWeight) continue;

            for (Edge edge : graph.get(currentNode)) {
                int neighborNode = edge.node;
                int edgeWeight = edge.weight;

                if (currentWeight + edgeWeight < dist[neighborNode]) {
                    dist[neighborNode] = currentWeight + edgeWeight;
                    queue.add(new Edge(neighborNode, currentWeight + edgeWeight));
                }
            }
        }

        return dist;
    }

    private static class Edge implements Comparable<Edge> {
        int node;
        int weight;

        public Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

}
