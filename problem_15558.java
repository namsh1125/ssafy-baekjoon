import java.io.*;
import java.util.*;

public class Main {

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 1 ≤ N ≤ 100,000
        int k = Integer.parseInt(st.nextToken()); // 1 ≤ k ≤ 100,000

        boolean[][] isMovable = new boolean[2][N]; // 0: 왼쪽, 1: 오른쪽
        for (int i = 0; i < 2; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                isMovable[i][j] = line.charAt(j) == '1';
            }
        }

        boolean[][] visited = new boolean[2][N];

        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(Position.LEFT, 0, 0));

        boolean canClear = false;
        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.index < 0) continue;
            if (state.index < state.time) continue;

            if (state.index > N - 1) {
                canClear = true;
                break;
            }

            if (!isMovable[state.position.getValue()][state.index]) continue;

            if (visited[state.position.getValue()][state.index]) continue;
            visited[state.position.getValue()][state.index] = true;

            queue.add(new State(state.position, state.index + 1, state.time + 1));
            queue.add(new State(state.position, state.index - 1, state.time + 1));

            Position nextPosition = state.position == Position.LEFT ? Position.RIGHT : Position.LEFT;
            queue.add(new State(nextPosition, state.index + k, state.time + 1));
        }

        System.out.print(canClear ? 1 : 0);
        br.close();
    }

    private static class State implements Comparable<State> {
        Position position;
        int index;
        int time;

        public State(Position position, int index, int time) {
            this.position = position;
            this.index = index;
            this.time = time;
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(this.time, o.time);
        }
    }

    // 위치를 나타내는 클래스
    enum Position {
        LEFT(0), RIGHT(1);

        private final int value;

        Position(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
