import java.io.*;
import java.util.*;

public class Main {

    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int VIRUS = 2;

    private static int N; // 연구소의 크기 (5 ≤ N ≤ 50)
    private static int M; // 놓을 수 있는 바이러스의 개수 (1 ≤ M ≤ 10)

    private static int[][] map;
    private static int emptyCount = 0; // 빈 칸의 개수
    private static List<Position> possibleVirusPositions = new ArrayList<>(); // 잠재적 바이러스 위치

    private static final int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우

    private static int minTime = Integer.MAX_VALUE;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                switch (map[i][j]) {
                    case VIRUS:
                        possibleVirusPositions.add(new Position(i, j)); // emptyCount를 추가하기 위해 의도적으로 break 생략
                    case EMPTY:
                        emptyCount++;
                        break;
                }
            }
        }

        combination(0, 0, new Position[M]); // M개의 바이러스를 놓는 모든 조합을 구해 최소 시간을 계산한다.
        if (minTime == Integer.MAX_VALUE) {
            minTime = -1;
        }

        System.out.print(minTime);
        br.close();
    }

    private static void combination(int index, int count, Position[] selected) {
        if (count == M) {
            bfs(selected);
            return;
        }

        for (int i = index; i < possibleVirusPositions.size(); i++) {
            selected[count] = possibleVirusPositions.get(i);
            combination(i + 1, count + 1, selected);
        }
    }

    private static void bfs(Position[] selected) {
        boolean[][] visited = new boolean[N][N];

        ArrayDeque<Position> queue = new ArrayDeque<>();
        for (Position pos : selected) {
            queue.add(pos);
        }

        int time = 0, visitedCount = 0;
        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (visited[current.i][current.j]) continue;
            visited[current.i][current.j] = true;
            time = Math.max(time, current.count);
            visitedCount++;

            for (int d = 0; d < 4; d++) {
                int ni = current.i + di[d];
                int nj = current.j + dj[d];

                if (!isInRange(ni, nj)) continue;
                if (map[ni][nj] == WALL || visited[ni][nj]) continue;

                queue.add(new Position(ni, nj, current.count + 1));
            }
        }

        if (visitedCount == emptyCount) { // 바이러스가 모두 퍼진 경우
            minTime = Math.min(minTime, time);
        }

    }

    private static boolean isInRange(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }

    private static class Position {
        int i, j;
        int count; // 현재 위치까지 오는데 걸린 시간

        public Position(int i, int j) {
            this(i, j, 0);
        }

        public Position(int i, int j, int count) {
            this.i = i;
            this.j = j;
            this.count = count;
        }
    }

}
