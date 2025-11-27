import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 월드 나라의 도시의 개수, 1 <= n <= 10,000
    private static int M; // 월드 나라의 도로의 개수, 1 <= m <= 100,000

    private static int[] inDegree; // 진입차수
    private static List<Integer>[] graph;
    private static List<Integer>[] reverseGraph;
    private static int[][] edges;
    private static int[] time; // 특정 도시까지 도착하는데 걸리는 최대 시간

    private static int hangeoleum; // 한걸음, 어떤 도시에도 갈 수 있음
    private static int roma; // 로마, 어떤 도시에서 출발해도 도착할 수 있음

    public static void main(String[] args) throws IOException { // 시간 제한: 2초, 메모리: 512MB
        init();

        int totalTime = getTotalTime();
        int edgeCount = getGoldRoadsCount();

        System.out.println(totalTime);
        System.out.println(edgeCount);
    }

    /**
     * 입력을 처리하는 메서드
     */
    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        inDegree = new int[N];
        graph = new ArrayList[N];
        reverseGraph = new ArrayList[N];
        edges = new int[M][3];
        time = new int[N];

        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        StringTokenizer st;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken()) - 1; // 1 <= u <= 10,000, 0-based-index
            int v = Integer.parseInt(st.nextToken()) - 1; // 1 <= v <= 10,000, 0-based-index
            int w = Integer.parseInt(st.nextToken()); // 1 <= w <= 10,000

            // 간선 정보 입력
            edges[i][0] = u;
            edges[i][1] = v;
            edges[i][2] = w;

            // 그래프 정보 입력
            graph[u].add(i);
            reverseGraph[v].add(i);

            // 진입차수 증가
            inDegree[v]++;
        }

        st = new StringTokenizer(br.readLine());
        hangeoleum = Integer.parseInt(st.nextToken()) - 1; // 0-based-index
        roma = Integer.parseInt(st.nextToken()) - 1; // 0-based-index

        br.close();
    }

    /**
     * 로마에 모두 도착하는 데 걸리는 시간을 구하는 메서드
     */
    private static int getTotalTime() {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(hangeoleum);

        // 로마로 도착할 수 있기 때문에, 위상정렬로도 풀림.
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            for (int edgeIndex : graph[currentNode]) {
                int neighborNode = edges[edgeIndex][1];
                int edgeWeight = edges[edgeIndex][2];

                inDegree[neighborNode]--;
                time[neighborNode] = Math.max(time[neighborNode], time[currentNode] + edgeWeight);

                if (inDegree[neighborNode] == 0) {
                    queue.add(neighborNode);
                }
            }
        }

        return time[roma];
    }

    /**
     * 황금을 칠해야 할 도로의 수를 구하는 메서드
     */
    private static int getGoldRoadsCount() {
        // 역추적으로 황금을 칠해야 할 도시의 수 구함
        boolean[] colored = new boolean[M];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(roma);

        boolean[] visited = new boolean[M];
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            if (currentNode == hangeoleum) continue;

            for (int edgeIndex : reverseGraph[currentNode]) {
                if (visited[edgeIndex]) continue;
                visited[edgeIndex] = true;

                int neighborNode = edges[edgeIndex][0];
                int edgeWeight = edges[edgeIndex][2];

                if (time[neighborNode] == time[currentNode] - edgeWeight) {
                    colored[edgeIndex] = true;
                    queue.add(neighborNode);
                }
            }
        }

        int count = 0;
        for (boolean isColored : colored) {
            if (isColored) count++;
        }

        return count;
    }

}
