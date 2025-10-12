import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 컴퓨터의 수

        List<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        int M = Integer.parseInt(br.readLine()); // 네트워크 상에서 직접 연결되어 있는 컴퓨터 쌍의 수
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].add(b);
            graph[b].add(a);
        }

        boolean[] visited = new boolean[N + 1];
        int answer = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1); // 1번 컴퓨터를 통해 감염 시작

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (visited[current]) continue;
            visited[current] = true;
            answer++;

            for (int next : graph[current]) {
                queue.add(next);
            }
        }

        System.out.print(answer - 1); // 1번 컴퓨터 제외
        br.close();
    }

}
