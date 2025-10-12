import java.io.*;
import java.util.*;

public class Main {

    private static int INF = 999_999_999;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 유저의 수
        int M = Integer.parseInt(st.nextToken()); // 친구 관계의 수

        long[][] relations = new long[N + 1][N + 1];
        for (long[] relation : relations) {
            Arrays.fill(relation, INF);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            relations[a][b] = 1;
            relations[b][a] = 1;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (relations[i][k] != 0 && relations[k][j] != 0) { // 누군가를 통해서 알 수 있는 경우 (i -> j)
                        relations[i][j] = Math.min(relations[i][j], relations[i][k] + relations[k][j]);
                        relations[j][i] = relations[i][j]; // 무방향 그래프
                    }
                }
            }
        }

        long minValue = Integer.MAX_VALUE; // 켈빈 베이컨의 수가 가장 작은 사람의 켈빈 베이컨의 수
        int minPerson = -1; // 켈빈 베이컨의 수가 가장 작은 사람
        for (int i = 1; i <= N; i++) {
            long sum = Arrays.stream(relations[i]).sum();

            if (sum < minValue) {
                minValue = sum;
                minPerson = i;
            }
        }

        System.out.print(minPerson);
        br.close();
    }

}
