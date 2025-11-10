import java.io.*;
import java.util.*;

public class Main {

    private static final int RIGHT = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;

    private static final int[] di = {0, 1, 0, -1}; // 우, 하, 좌, 상
    private static final int[] dj = {1, 0, -1, 0}; // 우, 하, 좌, 상

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 보드의 크기, 2 ≤ N ≤ 100
        int K = Integer.parseInt(br.readLine()); // 사과의 개수, 0 ≤ K ≤ 100

        boolean[][] apple = new boolean[N][N];
        for (int i = 0; i < K; i++) {
            String[] applePos = br.readLine().split(" ");
            int row = Integer.parseInt(applePos[0]) - 1;
            int col = Integer.parseInt(applePos[1]) - 1;

            apple[row][col] = true; // 사과 위치 표시
        }

        char[] changes = new char[10000]; // 최대 10000초 동안의 방향 변화 기록

        int L = Integer.parseInt(br.readLine()); // 방향 변환 횟수, 1 ≤ L ≤ 100
        for (int i = 0; i < L; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            char direction = st.nextToken().charAt(0);

            changes[time] = direction; // 해당 시간에 방향 변화 기록
        }

        int time = 1;
        Snake snake = new Snake();
        while (true) {
            Position head = snake.body.peekFirst();
            int ni = head.i + di[snake.direction];
            int nj = head.j + dj[snake.direction];

            if (!isInRange(ni, nj, N)) break; // 벽과 충돌 시 종료
            if (snake.bodySet.contains(ni * N + nj)) break; // 몸통과 충돌 시 종료

            // 뱀 이동
            snake.body.addFirst(new Position(ni, nj));
            snake.bodySet.add(ni * N + nj);

            if (apple[ni][nj]) { // 사과가 있는 경우
                apple[ni][nj] = false;

            } else {
                // 꼬리 제거
                Position tail = snake.body.removeLast();
                snake.bodySet.remove(tail.i * N + tail.j);
            }

            // 방향 전환
            if (changes[time] == 'L') {
                snake.direction = (snake.direction + 3) % 4; // 왼쪽으로 90도 회전
            } else if (changes[time] == 'D') {
                snake.direction = (snake.direction + 1) % 4; // 오른쪽으로 90도 회전
            }

            time++;
        }

        System.out.println(time);
        br.close();
    }

    private static boolean isInRange(int i, int j, int N) {
        return 0 <= i && i < N && 0 <= j && j < N;
    }

    private static class Snake {
        ArrayDeque<Position> body;
        Set<Integer> bodySet; // 몸통 위치를 빠르게 확인하기 위한 집합
        int length; // 현재 뱀의 길이
        int direction; // 현재 방향

        public Snake() {
            ArrayDeque<Position> body = new ArrayDeque<>();
            body.add(new Position(0, 0));
            this.body = body;

            Set<Integer> bodySet = new HashSet<>();
            bodySet.add(0); // 시작 위치 (0,0)

            this.bodySet = bodySet;
            this.length = 1;
            this.direction = RIGHT;
        }
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

}
