import java.io.*;
import java.util.PriorityQueue;

public class Main {

    private static int N;
    private static int[][] length;
    private static int[] parents;
    private static int[] ranks;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        length = new int[N][N];
        for (int i = 0; i < N; i++) {
            char[] c = br.readLine().toCharArray();
            for (int j = 0; j < N; j++) {
                length[i][j] = toIntLength(c[j]);
            }
        }

        parents = new int[N];
        ranks = new int[N];
        for (int i = 0; i < N; i++) {
            parents[i] = i;
        }

        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += length[i][j];
            }
        }

        int result = createMST();
        System.out.println(result != -1 ? sum - result : -1);

        br.close();
    }

    private static int createMST() {
        PriorityQueue<Edge> queue = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (length[i][j] == 0) continue;
                queue.add(new Edge(i, j, length[i][j]));
            }
        }

        int length = 0;
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();

            if (find(edge.from) != find(edge.to)) {
                union(edge.from, edge.to);
                length += edge.weight;
            }
        }

        // 모든 정점들이 연결되어 있는지 확인
        for (int i = 1; i < N; i++) {
            if (find(0) != find(i)) {
                return -1;
            }
        }

        return length;
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

    private static int toIntLength(char c) {
        if (Character.isLowerCase(c)) return c - 'a' + 1;
        if (Character.isUpperCase(c)) return c - 'A' + 27;

        return 0;
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
