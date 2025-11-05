import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 통신탑의 개수, 1 ≤ N ≤ 100,000
    private static int[] parents;
    private static int[] ranks;
    private static int[] sizes;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken()); // 통신탑 사이의 연결의 개수, 1 ≤ M ≤ 200,000
        int Q = Integer.parseInt(st.nextToken()); // 통신망 연결 분할 횟수, 1 ≤ Q ≤ M

        Edge[] edges = new Edge[M];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            edges[i] = new Edge(x, y);
        }

        boolean[] isRemoved = new boolean[M];
        ArrayDeque<Integer> queue = new ArrayDeque<>(); // 제거된 간선의 인덱스를 저장하는 큐
        for (int i = 0; i < Q; i++) {
            int a = Integer.parseInt(br.readLine());
            isRemoved[a - 1] = true;
            queue.add(a - 1);
        }

        parents = new int[N + 1];
        ranks = new int[N + 1];
        sizes = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parents[i] = i;
            ranks[i] = 0;
            sizes[i] = 1;
        }

        // 제거되지 않은 간선들로 초기 Union-Find 구성
        for (int i = 0; i < M; i++) {
            if (!isRemoved[i]) {
                union(edges[i].from, edges[i].to);
            }
        }

        // 제거된 간선들을 역순으로 추가하며 연결 상태 확인
        long cost = 0;
        while (!queue.isEmpty()) {
            int index = queue.poll();

            int root1 = find(edges[index].from);
            int root2 = find(edges[index].to);

            if (root1 != root2) {
                cost += (long) sizes[root1] * sizes[root2];
                union(root1, root2);
            }
        }

        System.out.print(cost);
        br.close();
    }

    private static void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        if (root1 == root2) return;

        if (ranks[root1] < ranks[root2]) {
            parents[root1] = root2;
            sizes[root2] += sizes[root1];

        } else if (ranks[root1] > ranks[root2]) {
            parents[root2] = root1;
            sizes[root1] += sizes[root2];

        } else {
            parents[root2] = root1;
            ranks[root1]++;
            sizes[root1] += sizes[root2];
        }
    }

    private static int find(int node) {
        if (node != parents[node]) parents[node] = find(parents[node]);
        return parents[node];
    }

    private static class Edge {
        int from;
        int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

}
