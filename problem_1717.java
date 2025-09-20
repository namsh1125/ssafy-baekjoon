import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int[] parents;
    private static int[] ranks;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]); // 입력으로 주어지는 연산의 개수

        parents = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            parents[i] = i;
        }

        ranks = new int[N + 1];
        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            int command = Integer.parseInt(input[0]);
            int a = Integer.parseInt(input[1]);
            int b = Integer.parseInt(input[2]);

            if (command == 0) union(a, b);
            else System.out.println(find(a) == find(b) ? "YES" : "NO");
        }

        br.close();
    }

    private static void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        if (root1 == root2) return;

        if (ranks[root1] < ranks[root2]) { // root2로 합치기
            parents[root1] = root2;

        } else if (ranks[root1] > ranks[root2]) { // root1로 합치기
            parents[root2] = root1;

        } else { // rank가 같은 경우 => 하나로 합치고 rank + 1
            parents[root2] = root1;
            ranks[root1]++;
        }

    }

    private static int find(int node) {
        if (node != parents[node]) parents[node] = find(parents[node]);
        return parents[node];
    }
}
