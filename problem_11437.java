import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static List<Integer>[] tree;
    private static int[] parents;
    private static int[] heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        tree = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            tree[a].add(b);
            tree[b].add(a);
        }

        parents = new int[N + 1];
        heights = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parents[i] = i;
            heights[i] = -1;
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(1, 1)); // 루트 노드

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (heights[current.node] != -1) continue;
            heights[current.node] = current.height;

            for (int neighbor : tree[current.node]) {
                if (heights[neighbor] == -1) {
                    parents[neighbor] = current.node;
                    queue.add(new Node(neighbor, current.height + 1));
                }
            }

        }

        StringBuilder sb = new StringBuilder();

        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(lca(a, b)).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static int lca(int node1, int node2) {
        int diff = Math.abs(heights[node1] - heights[node2]);

        if (heights[node1] > heights[node2]) {
            node1 = moveUp(node1, diff);
        } else if(heights[node2] > heights[node1])  {
            node2 = moveUp(node2, diff);
        }

        while (node1 != node2) {
            node1 = moveUp(node1, 1);
            node2 = moveUp(node2, 1);
        }

        return node1;
    }

    private static int moveUp(int node, int count) {
        while (count -- > 0) {
            node = parents[node];
        }

        return node;
    }

    private static class Node implements Comparable<Node> {
        int node;
        int height;

        public Node(int node, int height) {
            this.node = node;
            this.height = height;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.height, o.height);
        }
    }

}
