import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static int r;
    private static int c;

    private static char[][] graph;

    private static char BLOCK = 'X';
    private static char PIPE = 'P';
    private static char ROAD = '.';

    // 최대한 우상향으로 가야 -> 가스관을 최대한 많이 연결할 수 있음
    private static int[] di = new int[]{-1, 0, 1}; // 우상, 우, 우하
    private static int[] dj = new int[]{1, 1, 1}; // 우상, 우, 우하

    private static int count = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        r = Integer.parseInt(s[0]); // 행
        c = Integer.parseInt(s[1]); // 열

        graph = new char[r][c];
        for (int i = 0; i < r; i++) {
            s = br.readLine().split("");
            for (int j = 0; j < c; j++) {
                graph[i][j] = s[j].charAt(0);
            }
        }

        // 첫째 열은 근처 빵집의 가스관, 마지막 열은 원웅이의 빵집
        for (int i = 0; i < r; i++) {
            dfs(new int[]{i, 0}, new int[c][2], 0); // (i, 0) 그래프 탐색
        }

        System.out.println(count);
        br.close();
    }

    private static boolean dfs(int[] current, int[][] path, int depth) {
        if (depth == c - 1) { // 원웅이의 빵집에 도착했다면
            count++;

            // 파이프라인 구축
            graph[current[0]][current[1]] = PIPE;
            for (int[] pos : path) {
                graph[pos[0]][pos[1]] = PIPE;
            }

            return true;
        }

        // 원웅이의 빵집에 아직 도착하지 않은 경우
        boolean pipeLineBuilt = false;
        path[depth][0] = current[0];
        path[depth][1] = current[1];

        for (int k = 0; k < 3; k++) {
            int ni = current[0] + di[k];
            int nj = current[1] + dj[k];

            if (pipeLineBuilt) { // 파이프라인이 구축된 경우
                break;
            }

            if (isInRange(ni, nj) && graph[ni][nj] == ROAD) { // 다음 방향으로 갈 수 있고
                pipeLineBuilt = dfs(new int[]{ni, nj}, path, depth + 1);
            }
        }

        if (!pipeLineBuilt) { // 파이프라인을 구축할 수 없는 경우 -> 이후 탐색 최적화를 위해 막기
            graph[current[0]][current[1]] = BLOCK;
        }

        path[depth][0] = -1;
        path[depth][1] = -1;
        return pipeLineBuilt;
    }

    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < r && 0 <= j && j < c;
    }

}
