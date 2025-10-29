import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int S;
    private static int E;

    private static Map<Integer, List<Integer>> teleport;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        teleport = new HashMap<>();
        for (int i = 0; i <= N; i++) {
            teleport.put(i, new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            teleport.get(x).add(y);
            teleport.get(y).add(x);
        }

        int result = bfs();

        System.out.println(result);
        br.close();
    }

    private static int bfs() {
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(new State(S, 0));

        boolean[] visited = new boolean[N + 1];

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.num == E) return state.time;
            if (visited[state.num]) continue;
            visited[state.num] = true;

            if (!teleport.get(state.num).isEmpty()) {
                for (int pos : teleport.get(state.num)) {
                    if (visited[pos]) continue;
                    queue.add(new State(pos, state.time + 1));
                }
            }

            int next = state.num + 1;
            if (1 <= next && next <= N && !visited[next]) queue.add(new State(next, state.time + 1));

            next = state.num - 1;
            if (1 <= next && next <= N && !visited[next]) queue.add(new State(next, state.time + 1));
        }

        return -1;
    }

    private static class State implements Comparable<State> {
        int num;
        int time;

        public State(int num, int time) {
            this.num = num;
            this.time = time;
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(this.time, o.time);
        }
    }

}
