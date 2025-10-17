import java.io.*;
import java.util.*;

public class Main {

    public static int N;
    private static long[] arr;
    private static long[] prefixSum;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new long[N];
        prefixSum = new long[N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }

        long result = solution();

        System.out.println(result);
        br.close();
    }

    private static long solution() {
        if (prefixSum[N] % 4 != 0) { // 연속된 네 부분으로 나눴을 때 각 부분의 합이 다른 경우
            return 0;
        }

        long targetSum = prefixSum[N] / 4;

        long count1 = 0; // i까지 누적합이 targetSum인 경우
        long count2 = 0; // i까지 누적합이 2 * targetSum인 경우
        long answer = 0;

        for (int i = 1; i < N; i++) { // N까지 가면 네 부분으로 나눌 수 없음
            if (prefixSum[i] == 3 * targetSum) answer += count2; // 세 번째 분할 지점까지 오기 전에 만들 수 있었던 '첫 두 부분'의 경우의 수 추가
            if (prefixSum[i] == 2 * targetSum) count2 += count1; // 두 번째 분할 지점 지점까지 오기 전에 만들 수 있었던 '첫 부분'의 경우의 수 추가
            if (prefixSum[i] == targetSum) count1++; // 첫 번째 분할 지점을 만들 수 있는 새로운 경우의 수 추가
        }

        return answer;
    }

}
