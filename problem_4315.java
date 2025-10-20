import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int[] marbles; // 각 노드의 구슬 개수
    private static List<Integer>[] children; // 각 노드의 자식 노드 리스트
    private static boolean[] isChild; // 자식 노드 여부 (루트 노드 찾기용)
    private static long totalMoves; // 총 이동 횟수

    public static void main(String[] args) throws Exception { // 시간 제한: 1초, 메모리 제한: 128 MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            N = Integer.parseInt(st.nextToken());
            if (N == 0) break; // 정점의 개수

            // 자료구조 초기화
            marbles = new int[N + 1];
            children = new ArrayList[N + 1];
            isChild = new boolean[N + 1];
            totalMoves = 0;

            for (int i = 1; i <= N; i++) {
                children[i] = new ArrayList<>();
            }

            // N개의 노드 정보 입력
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                int v = Integer.parseInt(st.nextToken()); // 정점의 번호

                marbles[v] = Integer.parseInt(st.nextToken()); // 박스에 들어 있는 구슬의 개수
                int childCount = Integer.parseInt(st.nextToken()); // 자식 노드의 개수

                for (int j = 0; j < childCount; j++) {
                    int child = Integer.parseInt(st.nextToken());
                    children[v].add(child);
                    isChild[child] = true;
                }
            }

            // 루트 노드 찾기
            int root = -1;
            for (int i = 1; i <= N; i++) {
                if (!isChild[i]) {
                    root = i;
                    break;
                }
            }

            // DFS로 구슬 이동 계산
            if (root != -1) {
                dfs(root);
            }

            sb.append(totalMoves).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static int dfs(int node) {
        int balance = marbles[node] - 1; // 나의 잔고 (구슬 개수 - 1)

        for (int child : children[node]) {
            int childBalance = dfs(child); // 자식 서브트리의 잔고
            totalMoves += Math.abs(childBalance); // 자식과의 이동 횟수 누적
            balance += childBalance; // 나의 잔고에 자식의 잔고 합산
        }

        return balance; // 나의 서브트리 전체의 잔고를 부모에게 반환
    }

}
