import java.io.*;
import java.util.*;

public class Main {

    private static int N, M, K;
    private static char[][] map;

    private static final int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우

    private static final char WALL = '#';

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new char[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            String line = br.readLine();
            for (int j = 1; j <= M; j++) {
                map[i][j] = line.charAt(j - 1);
            }
        }

        st = new StringTokenizer(br.readLine());
        Position start = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        Position end = new Position(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        System.out.println(bfs(start, end));
    }

    private static int bfs(Position start, Position end) {
        // 1. dist 배열을 사용해 최소 시간 기록 및 방문 여부 확인
        int[][] dist = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], -1);
        }

        ArrayDeque<Position> queue = new ArrayDeque<>();

        // 2. 시작점 초기화
        dist[start.i][start.j] = 0;
        queue.offer(start);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.i == end.i && current.j == end.j) {
                return dist[current.i][current.j];
            }

            // 3. 4방향 탐색
            for (int i = 0; i < 4; i++) {
                // 4. 최대 K칸 이동
                for (int k = 1; k <= K; k++) {
                    int ni = current.i + di[i] * k;
                    int nj = current.j + dj[i] * k;

                    // 범위 벗어나거나 벽 만나면 해당 방향 탐색 중단
                    if (!isInRange(ni, nj) || map[ni][nj] == WALL) {
                        break;
                    }

                    // 5. 핵심 최적화
                    // 이미 더 빠른 경로로 방문했다면, 이 방향으로는 더 볼 필요 없음
                    if (dist[ni][nj] != -1 && dist[ni][nj] <= dist[current.i][current.j]) {
                        break;
                    }

                    // 아직 방문하지 않은 곳이라면 큐에 추가
                    if (dist[ni][nj] == -1) {
                        dist[ni][nj] = dist[current.i][current.j] + 1;
                        queue.offer(new Position(ni, nj));
                    }
                }
            }
        }

        return -1; // 도착점에 도달할 수 없는 경우
    }

    private static boolean isInRange(int i, int j) {
        return 1 <= i && i <= N && 1 <= j && j <= M;
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
