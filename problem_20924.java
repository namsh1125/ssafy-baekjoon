import java.io.*;
import java.util.*;

public class Main {

    private static final int INF = Integer.MAX_VALUE / 2;
    private static final int NOT_FOUND = -1;

    public static void main(String[] args) throws IOException { // 시간 제한: 2.5초, 메모리: 1024MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 노드의 개수, 1 <= N <= 200,000
        int R = Integer.parseInt(st.nextToken()) - 1; // 루트 노드의 번호, 0-based-index

        // 트리 생성
        List<Edge>[] tree = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken()) - 1; // 0-based-index
            int b = Integer.parseInt(st.nextToken()) - 1; // 0-based-index
            int d = Integer.parseInt(st.nextToken()); // 간선의 길이, 1 <= d <= 1000

            tree[a].add(new Edge(b, d));
            tree[b].add(new Edge(a, d));
        }

        // 루트 노드에서부터 노드까지의 거리
        int[] dist = new int[N];
        Arrays.fill(dist, INF);
        dist[R] = 0;

        // 루트 노드에서부터 노드까지의 거리 계산
        ArrayDeque<Edge> queue = new ArrayDeque<>();
        queue.add(new Edge(R, 0));

        int gigaNode = -1;
        int lastVisitedNode = -1;
        while (!queue.isEmpty()) {
            Edge currentEdge = queue.poll();

            int currentNode = currentEdge.node;
            int currentWeight = currentEdge.weight;

            if (dist[currentNode] < currentWeight) continue;
            dist[currentNode] = currentWeight;

            // 기가 노드 여부 확인
            if (gigaNode == NOT_FOUND) {
                if (currentNode == R && tree[currentNode].size() >= 2) { // 루트 노드인 경우에는 2개 이상의 이동을 할 수 있는 경우가 기가노드
                    gigaNode = currentNode;
                } else if (currentNode != R && tree[currentNode].size() >= 3) { // 루트 노드가 아닌 경우 양방향으로 저장했으므로 3개 이상의 이동을 할 수 있는 경우가 기가노드
                    gigaNode = currentNode;
                }
            }

            for (Edge edge : tree[currentNode]) {
                if (dist[currentNode] + edge.weight < dist[edge.node]) {
                    dist[edge.node] = dist[currentNode] + edge.weight;
                    queue.add(new Edge(edge.node, dist[edge.node]));
                }
            }

            lastVisitedNode = currentNode;
        }

        // 리프 노드가 단 1개인 경우 리프 노드가 기가 노드
        if (gigaNode == NOT_FOUND) {
            gigaNode = lastVisitedNode;
        }

        int trunkLength = dist[gigaNode]; // 트리의 기둥: 루트 노드에서부터 기가 노드까지
        int branchLength = 0; // 트리의 가지: 기가 노드에서부터 임의의 리프 노드

        for (int i = 0; i < N; i++) {
            if (dist[i] - trunkLength >= 0) {
                branchLength = Math.max(branchLength, dist[i] - trunkLength);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(trunkLength).append(" ").append(branchLength);

        System.out.println(sb);
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
