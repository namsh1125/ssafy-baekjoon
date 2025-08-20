import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final int NONE = 0;
    private static final int CHEESE = 1;

    private static final int[] di = new int[]{-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = new int[]{0, 0, -1, 1}; // 상, 하, 좌, 우

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int R = Integer.parseInt(s[0]); // R <= 100
        int C = Integer.parseInt(s[1]); // C <= 100

        int count = 0;
        int[][] map = new int[R][C];
        for (int i = 0; i < R; i++) {
            s = br.readLine().split(" ");
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(s[j]);

                if (map[i][j] == CHEESE) count++;
            }
        }

        int time = 0, beforeCount = count;
        List<Position> edges = getEdgeCheese(map);
        while (!edges.isEmpty()) {
            time++;
            beforeCount = count;
            count -= edges.size();

            for (Position cheese : edges) {
                map[cheese.i][cheese.j] = NONE;
            }

            edges = getEdgeCheese(map);
        }

        System.out.println(time);
        System.out.println(beforeCount);
        br.close();
    }

    private static List<Position> getEdgeCheese(int[][] map) {
        boolean[][] visited = new boolean[map.length][map[0].length];

        ArrayDeque<Position> queue = new ArrayDeque<>();
        queue.add(new Position(0, 0));

        List<Position> edge = new ArrayList<>();
        while (!queue.isEmpty()) {
            Position curr = queue.poll();

            if (visited[curr.i][curr.j]) continue;
            visited[curr.i][curr.j] = true;

            for (int k = 0; k < 4; k++) {
                int ni = curr.i + di[k];
                int nj = curr.j + dj[k];

                if (isInRange(map, ni, nj) && !visited[ni][nj]) {
                    if (map[ni][nj] == CHEESE) {
                        edge.add(new Position(ni, nj));
                        visited[ni][nj] = true; // 추가로 치즈를 추가하는 경우를 방지하기 위함
                    } else queue.add(new Position(ni, nj));
                }
            }
        }

        return edge;
    }

    private static boolean isInRange(int[][] map, int i, int j) {
        return i >= 0 && i < map.length && j >= 0 && j < map[i].length;
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
