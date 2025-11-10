import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 거리에 있는 아이들의 수, 1 ≤ N ≤ 30,000
    private static int M; // 아이들의 친구 관계 수, 1 ≤ M ≤ 100,000
    private static int K; // 울음소리가 공명하기 위한 최소 아이의 수, 1 ≤ K ≤ min(N, 3,000)

    private static int[] c; // 아이들이 받은 사탕의 수, 1 ≤ c[i] ≤ 10,000

    private static int[] parents;
    private static int[] ranks;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        parents = new int[N];
        ranks = new int[N];
        for (int i = 0; i < N; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }

        c = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            c[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;

            union(a, b);
        }

        // 그룹별로 인원 수, 사탕의 합 계산
        int groupCount = 0;
        int[] groupSize = new int[N];
        long[] groupCandyCount = new long[N];
        int[] toIndex = new int[N];
        Arrays.fill(toIndex, -1);

        for (int i = 0; i < N; i++) {
            int root = find(i);
            if (toIndex[root] == -1) {
                toIndex[root] = groupCount++;
            }

            int groupIndex = toIndex[root];
            groupSize[groupIndex]++;
            groupCandyCount[groupIndex] += c[i];
        }

        // 배낭 문제로 변환하여 해결
        long[] dp = new long[K];
        for (int i = 0; i < groupCount; i++) {
            int size = groupSize[i];
            long candyCount = groupCandyCount[i];

            for (int j = K - 1; j >= size; j--) {
                dp[j] = Math.max(dp[j], dp[j - size] + candyCount);
            }
        }

        System.out.print(dp[K - 1]);
        br.close();
    }

    private static void union(int node1, int node2) {
        int root1 = find(node1);
        int root2 = find(node2);

        if (root1 == root2) return;

        if (ranks[root1] < ranks[root2]) {
            parents[root1] = root2;

        } else if (ranks[root1] > ranks[root2]) {
            parents[root2] = root1;

        } else {
            parents[root2] = root1;
            ranks[root1]++;
        }
    }

    private static int find(int node) {
        if (parents[node] != node) parents[node] = find(parents[node]);
        return parents[node];
    }

}
