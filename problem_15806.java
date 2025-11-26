import java.io.*;
import java.util.*;

public class Main {

    private static final int[] di = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] dj = {1, 2, 2, 1, -1, -2, -2, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 영우의 방의 크기, 1 ≤ N ≤ 300
        int M = Integer.parseInt(st.nextToken()); // 방에 있는 곰팡이의 개수, 0 ≤ M ≤ N^2 (90000)
        int K = Integer.parseInt(st.nextToken()); // 청소 검사 시스템이 검사하는 방바닥 좌표의 개수, 0 ≤ K ≤ N^2 (90000)
        int t = Integer.parseInt(st.nextToken()); // 청소 검사가 실시 되기까지 남은 일수, 1 ≤ t ≤ 10000

        // dist[i][j][0]: (r+c)가 짝수인 곳에서 출발한 곰팡이가 (i, j)까지 도달하는 최소 일수
        // dist[i][j][1]: (r+c)가 홀수인 곳에서 출발한 곰팡이가 (i, j)까지 도달하는 최소 일수
        int[][][] dist = new int[N][N][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dist[i][j][0] = -1;
                dist[i][j][1] = -1;
            }
        }

        // 곰팡이의 위치 입력
        ArrayDeque<Position> moldPositions = new ArrayDeque<>();
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1; // 0-based-index
            int y = Integer.parseInt(st.nextToken()) - 1; // 0-based-index

            int parity = (x + y) % 2;
            dist[y][x][parity] = 0;

            moldPositions.add(new Position(y, x, parity));
        }

        // BFS로 각 칸까지 도달하는 최소 일수 계산
        while (!moldPositions.isEmpty()) {
            Position pos = moldPositions.poll();

            for (int d = 0; d < 8; d++) {
                int ni = pos.i + di[d];
                int nj = pos.j + dj[d];

                if (ni < 0 || ni >= N || nj < 0 || nj >= N) continue;
                if (dist[ni][nj][pos.parity] != -1) continue;

                dist[ni][nj][pos.parity] = dist[pos.i][pos.j][pos.parity] + 1;
                moldPositions.add(new Position(ni, nj, pos.parity));
            }
        }

        // 감독관의 위치 확인
        boolean cleanNeeded = false;
        while (K-- > 0) {
            st = new StringTokenizer(br.readLine());
            int j = Integer.parseInt(st.nextToken()) - 1; // 0-based-index
            int i = Integer.parseInt(st.nextToken()) - 1; // 0-based-index

            int parity = (i + j) % 2;

            // 곰팡이 출신 계산
            //  t가 홀수면? parity가 다른 곳에서 출발한 곰팡이만 올 수 있음
            //  t가 짝수면? parity가 같은 곳에서 출발한 곰팡이만 올 수 있음
            int requiredOrigin = (t % 2 == 0) ? parity : 1 - parity;

            // 해당 출신의 곰팡이가 t일 이내에 도달할 수 있는지 확인
            if (dist[i][j][requiredOrigin] == -1) continue;
            if (dist[i][j][requiredOrigin] > t) continue;

            // 청소 필요
            if (N >= 3 || dist[i][j][requiredOrigin] == t) {
                cleanNeeded = true;
                break;
            }
        }

        System.out.println(cleanNeeded ? "YES" : "NO");
        br.close();
    }

    private static class Position {
        int i, j;
        int parity;

        public Position(int i, int j, int parity) {
            this.i = i;
            this.j = j;
            this.parity = parity;
        }
    }

}
