import java.io.*;
import java.util.*;

public class Main {

    private static int n; // 교차로, 2 ≤ n ≤ 2,000
    private static List<Edge>[] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine()); // 테스트 케이스 수, 1 ≤ T ≤ 100
        while (tc-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken()); // 도로, 1 ≤ m ≤ 50,000
            int t = Integer.parseInt(st.nextToken()); // 목적지 후보의 개수, 1 ≤ t ≤ 100

            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken()); // 출발지, 1 ≤ s ≤ n
            int g = Integer.parseInt(st.nextToken()); // 교차로, 1 ≤ g ≤ n
            int h = Integer.parseInt(st.nextToken()); // 교차로, 1 ≤ h ≤ n

            // 그래프 초기화
            graph = new ArrayList[n + 1];
            for (int i = 0; i <= n; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken()); // 1 ≤ a < b ≤ n
                int b = Integer.parseInt(st.nextToken()); // 1 ≤ a < b ≤ n
                int d = Integer.parseInt(st.nextToken()); // 1 ≤ a < b ≤ n, 1 ≤ d ≤ 1,000

                graph[a].add(new Edge(b, d));
                graph[b].add(new Edge(a, d));
            }

            // 목적지 후보
            int[] destinations = new int[t];
            for (int i = 0; i < t; i++) {
                int x = Integer.parseInt(br.readLine());
                destinations[i] = x;
            }

            // 다익스트라 알고리즘으로 최단 거리 계산 및 g-h 도로 경유 확인
            int[] distFromS = dijkstra(s);
            int[] distFromG = dijkstra(g);
            int[] distFromH = dijkstra(h);

            List<Integer> isValidDestination = new ArrayList<>();
            for (int dest : destinations) {
                int path1 = distFromS[g] + distFromG[h] + distFromH[dest];
                int path2 = distFromS[h] + distFromH[g] + distFromG[dest];
                if (distFromS[dest] == path1 || distFromS[dest] == path2) {
                    isValidDestination.add(dest);
                }
            }

            Collections.sort(isValidDestination);

            for (int dest : isValidDestination) {
                sb.append(dest).append(" ");
            }
            sb.append("\n");
        }

        System.out.print(sb.toString().trim());
        br.close();
    }

    private static int[] dijkstra(int start) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.add(new Edge(start, 0));

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
            int currentNode = current.node;
            int currentWeight = current.weight;

            if (currentWeight > dist[currentNode]) continue;

            for (Edge edge : graph[currentNode]) {
                int nextNode = edge.node;
                int newDist = currentWeight + edge.weight;

                if (newDist < dist[nextNode]) {
                    dist[nextNode] = newDist;
                    queue.add(new Edge(nextNode, newDist));
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
