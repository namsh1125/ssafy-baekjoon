import java.io.*;
import java.util.*;

public class Main {

    private static int M; // M <= 100
    private static int N; // N <= 100
    private static int[][] graph;

    private static Position start;
    private static Position end;

    private static final int EAST = 0;
    private static final int WEST = 1;
    private static final int SOUTH = 2;
    private static final int NORTH = 3;

    private static final int CAN_GO = 0; // 궤도가 있어 움직일 수 있는 지점

    private static final int[] di = {0, 0, 1, -1}; // 동, 서, 남, 북
    private static final int[] dj = {1, -1, 0, 0}; // 동, 서, 남, 북

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // 세로 길이
        N = Integer.parseInt(st.nextToken()); // 가로 길이
        graph = new int[M][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        start = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        st = new StringTokenizer(br.readLine());
        end = new Position(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        boolean[][][] visited = new boolean[M][N][4];

        ArrayDeque<Position> queue = new ArrayDeque<>();
        queue.add(start);

        int minCommand = -1;
        while (!queue.isEmpty()) {
            Position pos = queue.poll();

            if (visited[pos.i][pos.j][pos.dir]) continue;
            visited[pos.i][pos.j][pos.dir] = true;

            if (pos.i == end.i && pos.j == end.j && pos.dir == end.dir) {
                minCommand = pos.count;
                break;
            }

            // 명령 1 수행: 현재 향하고 있는 방향으로 k칸 만큼 움직인다. (k는 1, 2 또는 3일 수 있다.)
            for (int k = 1; k <= 3; k++) {
                int ni = pos.i + di[pos.dir] * k;
                int nj = pos.j + dj[pos.dir] * k;

                if (!isInRange(ni, nj)) break; // 범위 밖인 경우
                if (graph[ni][nj] != CAN_GO) break; // 가로막혀서 이동할 수 없는 경우
                if (visited[ni][nj][pos.dir]) continue; // 이미 방문한 경우

                queue.add(new Position(ni, nj, pos.dir, pos.count + 1));
            }

            // 명령 2 수행: 왼쪽 또는 오른쪽으로 90° 회전한다.
            // 왼쪽으로 도는 경우: 동, 서, 남, 북, 동, 서, 남, 북
            // 동 -> 북 (0 -> 3)
            // 북 -> 서 (3 -> 1)
            // 서 -> 남 (1 -> 2)
            // 남 -> 동 (2 -> 0)
            int leftDir = -1;
            if (pos.dir == 0) leftDir = 3;
            else if (pos.dir == 3) leftDir = 1;
            else if (pos.dir == 1) leftDir = 2;
            else if (pos.dir == 2) leftDir = 0;

            queue.add(new Position(pos.i, pos.j, leftDir, pos.count + 1));

            // 우측으로 도는 경우: 동, 서, 남, 북
            // 동 -> 남 (0 -> 2)
            // 남 -> 서 (2 -> 1)
            // 서 -> 북 (1 -> 3)
            // 북 -> 동 (3 -> 0)
            int rightDir = -1;
            if (pos.dir == 0) rightDir = 2;
            else if (pos.dir == 2) rightDir = 1;
            else if (pos.dir == 1) rightDir = 3;
            else if (pos.dir == 3) rightDir = 0;

            queue.add(new Position(pos.i, pos.j, rightDir, pos.count + 1));
        }

        System.out.println(minCommand);
        br.close();
    }

    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < M && 0 <= j && j < N;
    }

    private static class Position {
        int i, j;
        int dir;
        int count;

        public Position(int i, int j, int dir) {
            this(i, j, dir, 0);
        }

        public Position(int i, int j, int dir, int count) {
            this.i = i;
            this.j = j;
            this.dir = dir;
            this.count = count;
        }
    }

}
