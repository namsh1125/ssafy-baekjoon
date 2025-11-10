import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int M;
    private static int K;

    private static boolean[][] isMoveable;

    private static final int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        isMoveable = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            char[] line = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                isMoveable[i][j] = line[j] == '0';
            }
        }

        PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.add(new Position(0, 0, K));

        boolean[][] visited = new boolean[N][M];
        int[][] leftBomb = new int[N][M];

        int result = -1;
        while (!queue.isEmpty()) {
            Position position = queue.poll();

            if (position.i == N - 1 && position.j == M - 1) {
                result = position.moveCount;
                break;
            }

            if (visited[position.i][position.j] && leftBomb[position.i][position.j] >= position.leftBomb) continue;
            visited[position.i][position.j] = true;
            leftBomb[position.i][position.j] = position.leftBomb;

            for (int dir = 0; dir < 4; dir++) {
                int ni = position.i + di[dir];
                int nj = position.j + dj[dir];

                if (!isInRange(ni, nj)) continue;
                if (isMoveable[ni][nj]) {
                    queue.add(new Position(ni, nj, position.leftBomb, position.moveCount + 1));
                } else {
                    if (position.leftBomb > 0) {
                        queue.add(new Position(ni, nj, position.leftBomb - 1, position.moveCount + 1));
                    }
                }
            }

        }

        System.out.println(result);
        br.close();
    }

    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < N && 0 <= j && j < M;
    }

    private static class Position implements Comparable<Position> {
        int i, j;
        int leftBomb;
        int moveCount;

        public Position(int i, int j, int leftBomb) {
            this(i, j, leftBomb, 1);
        }

        public Position(int i, int j, int leftBomb, int moveCount) {
            this.i = i;
            this.j = j;
            this.leftBomb = leftBomb;
            this.moveCount = moveCount;
        }

        @Override
        public int compareTo(Position o) {
            return Integer.compare(this.moveCount, o.moveCount);
        }
    }

}
