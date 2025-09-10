import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {

    private static char EMPTY = '.';
    private static char WATER = '*';
    private static char ROCK = 'X';
    private static char CAVE = 'D';
    private static char DOCHI = 'S';

    // 위, 아래, 오른쪽, 왼쪽
    private static int[] di = {-1, 1, 0, 0};
    private static int[] dj = {0, 0, 1, -1};

    private static int R;
    private static int C;
    private static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        map = new char[R][C];
        for (int i = 0; i < R; i++) {
            map[i] = br.readLine().toCharArray();
        }

        int result = bfs();
        System.out.println(result != -1 ? result : "KAKTUS");
        br.close();
    }

    private static int bfs() {
        ArrayDeque<Position> dochiQueue = new ArrayDeque<>();
        ArrayDeque<Position> waterQueue = new ArrayDeque<>();

        boolean[][] dochiVisited = new boolean[R][C];
        boolean[][] waterVisited = new boolean[R][C];

        // 초기 위치 설정 및 visited 처리
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == WATER) {
                    waterQueue.add(new Position(i, j));
                    waterVisited[i][j] = true;

                } else if (map[i][j] == DOCHI) {
                    dochiQueue.add(new Position(i, j));
                    dochiVisited[i][j] = true;
                }
            }
        }

        int time = 0;
        while (!dochiQueue.isEmpty()) {
            // 1. 먼저 물을 확산시킴
            int waterCount = waterQueue.size();
            while (waterCount-- > 0) {
                Position current = waterQueue.poll();

                for (int dir = 0; dir < 4; dir++) {
                    int ni = current.i + di[dir];
                    int nj = current.j + dj[dir];

                    if (!isInRange(ni, nj)) continue;

                    // 물은 빈 공간이나 고슴도치가 있는 곳으로 확산 가능
                    // 단, 돌(X)과 비버의 굴(D)로는 이동 불가
                    if (map[ni][nj] != ROCK && map[ni][nj] != CAVE && !waterVisited[ni][nj]) {
                        waterVisited[ni][nj] = true;
                        waterQueue.add(new Position(ni, nj));
                    }
                }
            }

            // 2. 고슴도치 이동
            int dochiCount = dochiQueue.size();
            while (dochiCount-- > 0) {
                Position current = dochiQueue.poll();

                for (int dir = 0; dir < 4; dir++) {
                    int ni = current.i + di[dir];
                    int nj = current.j + dj[dir];

                    if (!isInRange(ni, nj)) continue;

                    // 비버의 굴에 도착
                    if (map[ni][nj] == CAVE) {
                        return time + 1;
                    }

                    // 빈 공간이고, 물이 없고, 방문하지 않은 곳으로만 이동
                    if (map[ni][nj] == EMPTY && !waterVisited[ni][nj] && !dochiVisited[ni][nj]) {
                        dochiVisited[ni][nj] = true;
                        dochiQueue.add(new Position(ni, nj));
                    }
                }
            }

            time++;
        }

        return -1;
    }

    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < R && 0 <= j && j < C;
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

}
