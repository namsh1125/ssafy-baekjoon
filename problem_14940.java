import java.io.*;
import java.util.*;

public class Main {

    private static final int EMPTY = 1;
    private static final int WALL = 0;
    private static final int START = 2;

    private static int N;
    private static int M;
    private static int[][] graph;
    private static Position start = new Position(0, 0);

    private static int[][] result;

    private static int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우

    public static void main(String[] args) throws Exception {
        init();
        bfs();
        print();
    }

    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new int[N][M];
        result = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());

                if (graph[i][j] == START) {
                    start = new Position(i, j);
                }

                if (graph[i][j] == WALL) result[i][j] = 0;
                else result[i][j] = -1;
            }
        }

        br.close();
    }

    private static void bfs() {
        boolean[][] visited = new boolean[N][M];
        ArrayDeque<Position> queue = new ArrayDeque<>();

        queue.add(start);
        visited[start.i][start.j] = true;
        result[start.i][start.j] = 0;

        while (!queue.isEmpty()) {
            Position position = queue.poll();

            for (int d = 0; d < 4; d++) {
                int ni = position.i + di[d];
                int nj = position.j + dj[d];

                if (!isInRange(ni, nj)) continue;
                if (visited[ni][nj]) continue;
                if (graph[ni][nj] == WALL) continue;

                visited[ni][nj] = true;
                result[ni][nj] = position.distance + 1;
                queue.add(new Position(ni, nj, position.distance + 1));
            }
        }
    }


    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < N && 0 <= j && j < M;
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(result[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    private static class Position {
        int i, j;
        int distance;

        public Position(int i, int j) {
            this(i, j, 0);
        }

        public Position(int i, int j, int distance) {
            this.i = i;
            this.j = j;
            this.distance = distance;
        }
    }

}
