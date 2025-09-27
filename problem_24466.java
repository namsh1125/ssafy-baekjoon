import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // BigDecimal로 확률 저장
        BigDecimal[][] percents = new BigDecimal[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                percents[i][j] = BigDecimal.ZERO;
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            percents[u][v] = new BigDecimal(w).divide(new BigDecimal(100), 50, RoundingMode.HALF_UP);
        }

        BigDecimal[] dp = new BigDecimal[N];
        for (int i = 0; i < N; i++) {
            dp[i] = BigDecimal.ZERO;
        }
        dp[0] = BigDecimal.ONE;

        // 9일 동안 시뮬레이션
        for (int time = 1; time <= 9; time++) {
            BigDecimal[] temp = new BigDecimal[N];
            Arrays.fill(temp, BigDecimal.ZERO);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    temp[j] = temp[j].add(dp[i].multiply(percents[i][j]));
                }
            }

            dp = temp;
        }

        // 최대 확률 찾기
        List<Integer> maxPercentIndexList = new ArrayList<>();
        BigDecimal maxPercent = BigDecimal.ZERO;

        for (int i = 0; i < N; i++) {
            int compare = dp[i].compareTo(maxPercent);
            if (compare > 0) {
                maxPercentIndexList.clear();
                maxPercentIndexList.add(i);
                maxPercent = dp[i];

            } else if (compare == 0) {
                maxPercentIndexList.add(i);
            }
        }

        // 출력
        StringBuilder sb = new StringBuilder();
        for (Integer index : maxPercentIndexList) {
            sb.append(index).append(" ");
        }
        sb.append("\n");

        // 소수점 아래 18자리까지 출력 (19자리에서 내림)
        String result = maxPercent.setScale(18, RoundingMode.DOWN).toPlainString();
        sb.append(result);

        System.out.println(sb);
        br.close();
    }
}
