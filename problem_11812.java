import java.io.*;
import java.security.Key;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long N = Long.parseLong(st.nextToken()); // 1 ≤ N ≤ 10^15
        int K = Integer.parseInt(st.nextToken()); // 1 ≤ K ≤ 1,000
        int Q = Integer.parseInt(st.nextToken()); // 1 ≤ Q ≤ 100,000

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken()) - 1;
            long y = Long.parseLong(st.nextToken()) - 1;

            // K가 1인 경우 특별 처리 (일직선 트리)
            if (K == 1) {
                sb.append(Math.abs(x - y)).append("\n");
                continue;
            }

            // 각 노드의 깊이 구하기
            long depthX = getDepth(x, K);
            long depthY = getDepth(y, K);

            long distance = 0;

            // 더 깊은 노드를 같은 레벨로 올리기
            if (depthX > depthY) {
                distance += depthX - depthY;
                x = moveUp(x, depthX - depthY, K);

            } else if (depthY > depthX) {
                distance += depthY - depthX;
                y = moveUp(y, depthY - depthX, K);
            }

            // 같은 레벨에서 LCA 찾기
            while (x != y) {
                x = (x - 1) / K;
                y = (y - 1) / K;
                distance += 2;
            }

            sb.append(distance).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static long getDepth(long node, int K) {
        int depth = 1;
        while (node != 0) {
            depth++;
            node = (node - 1) / K;
        }

        return depth;
    }

    private static long moveUp(long node, long steps, int K) {
        for (long i = 0; i < steps; i++) {
            if (node == 0) break;
            node = (node - 1) / K;
        }

        return node;
    }
}
