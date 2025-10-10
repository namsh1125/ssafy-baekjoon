import java.io.*;
import java.util.*;

public class Main {

    private static int V;
    private static int E;

    private static int[] parents;
    private static int[] heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        parents = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            parents[i] = i;
        }

        heights = new int[V + 1];

        PriorityQueue<Edge> queue = new PriorityQueue<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // A - B : 간선 C (C는 음수일 수 있으며, 절댓값이 1,000,000을 넘지 않음)
            queue.add(new Edge(a, b, c));
        }

        long result = 0;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (find(edge.start) != find(edge.end)) {
                union(edge.start, edge.end);
                result += edge.weight;
            }
        }

        System.out.println(result);
        br.close();
    }

    private static void union(int node1, int node2) {
        int parent1 = find(node1);
        int parent2 = find(node2);

        if (parent1 == parent2) return;
        if (heights[parent1] > heights[parent2]) {
            parents[parent2] = parent1;
        } else if (heights[parent2] > heights[parent1]) {
            parents[parent1] = parent2;
        } else {
            parents[parent2] = parent1;
            heights[parent1]++;
        }
    }

    private static int find(int node) {
        if (node != parents[node]) parents[node] = find(parents[node]);
        return parents[node];
    }

    private static class Edge implements Comparable<Edge> {
        int start;
        int end;
        int weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

}
