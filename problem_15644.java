import java.io.*;
import java.util.*;

public class Main {

    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final char HOLE = 'O';
    private static final char RED = 'R';
    private static final char BLUE = 'B';

    private static int N; // 보드의 세로, 3 ≤ N ≤ 10
    private static int M; // 보드의 가로, 3 ≤ M ≤ 10
    private static char[][] map;
    private static boolean[][][][] visited;

    private static final int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우
    private static final char[] directions = {'U', 'D', 'L', 'R'};

    public static void main(String[] args) throws IOException { // 시간제한: 2초, 메모리 제한: 512MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M][N][M];

        int rx = 0, ry = 0, bx = 0, by = 0;
        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);

                if (map[i][j] == RED) {
                    rx = i;
                    ry = j;
                    map[i][j] = EMPTY; // 구슬 위치는 빈칸으로 둠 (좌표로만 관리)

                } else if (map[i][j] == BLUE) {
                    bx = i;
                    by = j;
                    map[i][j] = EMPTY;
                }
            }
        }

        String result = bfs(rx, ry, bx, by);

        System.out.print(result);
        br.close();
    }

    private static String bfs(int rx, int ry, int bx, int by) {
        ArrayDeque<State> queue = new ArrayDeque<>();
        queue.offer(new State(rx, ry, bx, by, 0, ""));
        visited[rx][ry][bx][by] = true;

        while (!queue.isEmpty()) {
            State current = queue.poll();

            if (current.count >= 10) continue; // 10번 이하로 움직인 경우만 탐색

            for (int dir = 0; dir < 4; dir++) {
                // 파란 구슬 이동
                int nbx = current.bx;
                int nby = current.by;

                while (map[nbx + di[dir]][nby + dj[dir]] != WALL) {
                    nbx += di[dir];
                    nby += dj[dir];

                    if (map[nbx][nby] == HOLE) break;
                }

                // 빨간 구슬 이동
                int nrx = current.rx;
                int nry = current.ry;
                while (map[nrx + di[dir]][nry + dj[dir]] != WALL) {
                    nrx += di[dir];
                    nry += dj[dir];

                    if (map[nrx][nry] == HOLE) break;
                }

                // 파란 구슬이 구멍에 빠진 경우 무시
                if (map[nbx][nby] == HOLE) continue;

                // 빨간 구슬이 구멍에 빠진 경우 성공
                if (map[nrx][nry] == HOLE) {
                    return (current.count + 1) + "\n" + (current.path + directions[dir]);
                }

                // 두 구슬이 같은 위치에 있는 경우
                if (nrx == nbx && nry == nby) {
                    // 이동 거리가 더 긴 구슬을 한 칸 뒤로 이동
                    int redDist = Math.abs(nrx - current.rx) + Math.abs(nry - current.ry);
                    int blueDist = Math.abs(nbx - current.bx) + Math.abs(nby - current.by);

                    if (redDist > blueDist) {
                        nrx -= di[dir];
                        nry -= dj[dir];
                    } else {
                        nbx -= di[dir];
                        nby -= dj[dir];
                    }
                }

                // 방문하지 않은 상태인 경우에만 큐에 추가
                if (!visited[nrx][nry][nbx][nby]) {
                    visited[nrx][nry][nbx][nby] = true;
                    queue.offer(new State(nrx, nry, nbx, nby, current.count + 1, current.path + directions[dir]));
                }
            }
        }

        return "-1";
    }

    private static class State {
        int rx, ry, bx, by;
        int count;
        String path;

        public State(int rx, int ry, int bx, int by, int count, String path) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.count = count;
            this.path = path;
        }
    }

}
