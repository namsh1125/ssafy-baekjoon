import java.io.*;
import java.util.*;

public class Main {

    private static final int INF = 51; // N ≤ 50 이므로

    public static void main(String[] args) throws Exception { // 시간 제한: 2초, 메모리 제한: 128MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // N ≤ 50
        Tree tree = new Tree(N);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int parent = Integer.parseInt(st.nextToken());
            if (parent != -1) {
                tree.nodes[parent].children.add(i);
            }
        }

        int[] dp = new int[N]; // dp[i]: i번 노드에서 시작했을 때, 모든 자식 노드들에게 뉴스를 전달하는데 걸리는 최소 시간
        for (int i = N - 1; i >= 0; i--) { // 리프 노드부터 거슬러 올라가며 계산 (직원 i의 상사 번호는 i보다 작거나 같은 음이 아닌 정수)
            Node node = tree.nodes[i];

            if (node.children.isEmpty()) {
                dp[i] = 0;

            } else {
                // 자식 노드들의 거리들을 내림차순으로 정렬
                List<Integer> childDistances = new ArrayList<>();
                for (int child : node.children) {
                    childDistances.add(dp[child]);
                }

                childDistances.sort(Collections.reverseOrder());

                // 자식 노드들의 거리 + 순서 인덱스 + 1 중 최댓값이 현재 노드의 거리
                int maxDistance = 0;
                for (int j = 0; j < childDistances.size(); j++) {
                    int distance = childDistances.get(j) + j + 1;
                    maxDistance = Math.max(maxDistance, distance);
                }

                dp[i] = maxDistance;
            }
        }

        System.out.println(dp[0]);
        br.close();
    }

    private static class Tree {
        Node[] nodes;

        public Tree(int size) {
            nodes = new Node[size];
            for (int i = 0; i < size; i++) {
                nodes[i] = new Node();
            }
        }
    }

    private static class Node {
        List<Integer> children;

        public Node() {
            this.children = new ArrayList<>();
        }
    }

}
