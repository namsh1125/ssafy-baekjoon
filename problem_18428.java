import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static char[][] graph;

    private static final char TEACHER = 'T';
    private static final char NONE = 'X';
    private static final char STUDENT = 'S';
    private static final char BLOCK = 'B';

    private static final int[] di = {-1, 1, 0, 0}; // 상, 하, 좌, 우
    private static final int[] dj = {0, 0, -1, 1}; // 상, 하, 좌, 우

    private static List<Position> teachers = new ArrayList<>(); // 선생님이 배치되어 있는 곳
    private static List<Position> availableList = new ArrayList<>(); // 감시탑이 배치될 수 있는 곳

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        graph = new char[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                graph[i][j] = st.nextToken().charAt(0);

                if (graph[i][j] == NONE) {
                    availableList.add(new Position(i, j));

                } else if (graph[i][j] == TEACHER) {
                    teachers.add(new Position(i, j));
                }
            }
        }

        String result = solution() ? "YES" : "NO";

        System.out.println(result);
        br.close();
    }

    private static boolean solution() {
        return backTracking(0, new Position[3], 0);
    }

    private static boolean backTracking(int index, Position[] obstacles, int size) {
        if (size >= 3) {
            for (Position pos : obstacles) {
                graph[pos.i][pos.j] = BLOCK;
            }

            boolean result = simulation();

            for (Position pos : obstacles) {
                graph[pos.i][pos.j] = NONE;
            }

            return result;
        }

        if (availableList.size() <= index) {
            return false;
        }

        obstacles[size] = availableList.get(index);
        boolean isAvailable = backTracking(index + 1, obstacles, size + 1);

        if (!isAvailable) {
            obstacles[size] = null;
            isAvailable = backTracking(index + 1, obstacles, size);
        }

        return isAvailable;
    }

    private static boolean simulation() {
        for (Position pos : teachers) {
            for (int dir = 0; dir < 4; dir++) {
                int ni = pos.i;
                int nj = pos.j;

                while (true) {
                    ni += di[dir];
                    nj += dj[dir];

                    if (!isInRange(ni, nj)) break;
                    if (graph[ni][nj] == BLOCK) break;

                    if (graph[ni][nj] == STUDENT) return false;
                }
            }
        }

        return true;
    }

    private static boolean isInRange(int i, int j) {
        return 0 <= i && i < N && 0 <= j && j < N;
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

}
