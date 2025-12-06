import java.io.*;
import java.util.*;

public class Main {

    private static Set<String> uniqueCombinations = new HashSet<>();

    private static final int[] di = {-1, 1, 0, 0};
    private static final int[] dj = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException { // 시간제한: 2초, 메모리 제한: 512MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[][] map = new char[5][5];
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();

            for (int j = 0; j < 5; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int[] selected = new int[7];
                selected[0] = i * 5 + j;

                boolean[][] visited = new boolean[5][5];
                visited[i][j] = true;

                simulation(map, selected, visited, 1);
            }
        }

        System.out.print(uniqueCombinations.size());
        br.close();
    }

    private static void simulation(char[][] map, int[] selected, boolean[][] visited, int index) {
        if (index == 7) {
            int sCount = 0;
            for (int i = 0; i < 7; i++) {
                int r = selected[i] / 5;
                int c = selected[i] % 5;

                if (map[r][c] == 'S') {
                    sCount++;
                }
            }

            if (sCount >= 4) {
                int[] temp = selected.clone();
                Arrays.sort(temp);

                StringBuilder sb = new StringBuilder();
                for (int val : temp) {
                    sb.append(val).append(",");
                }

                uniqueCombinations.add(sb.toString());
            }

            return;
        }

        for (int i = 0; i < index; i++) {
            int r = selected[i] / 5;
            int c = selected[i] % 5;

            for (int d = 0; d < 4; d++) {
                int nr = r + di[d];
                int nc = c + dj[d];

                if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;

                if (visited[nr][nc]) continue;
                int nextIndex = nr * 5 + nc;

                visited[nr][nc] = true;
                selected[index] = nextIndex;
                simulation(map, selected, visited, index + 1);
                visited[nr][nc] = false;
            }
        }
    }

}
