import java.io.*;
import java.util.*;

public class Main {

    private static final int INF = 999_999_999;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[] dp = new int[N + 1];
        Arrays.fill(dp, INF);

        dp[N] = 0;
        ArrayDeque<Number> queue = new ArrayDeque<>();
        queue.add(new Number(N, 0));

        while (!queue.isEmpty()) {
            Number cur = queue.poll();

            // 방문 처리
            if (cur.count > dp[cur.num]) continue;
            dp[cur.num] = cur.count;

            // X가 3으로 나누어 떨어지는 경우
            if (cur.num % 3 == 0) {
                queue.add(new Number(cur.num / 3, cur.count + 1));
            }

            // X가 2로 나누어 떨어지는 경우
            if (cur.num % 2 == 0) {
                queue.add(new Number(cur.num / 2, cur.count + 1));
            }

            // 1을 빼는 경우
            if (cur.num > 1) {
                queue.add(new Number(cur.num - 1, cur.count + 1));
            }

        }

        System.out.println(dp[1]);
        sc.close();
    }

    private static class Number {
        int num;
        int count;

        public Number(int num, int count) {
            this.num = num;
            this.count = count;
        }
    }

}
