import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException { // 시간제한: 12초, 메모리 제한: 1024MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 배열의 크기 (1 ≤ N ≤ 4000)

        int[] A = new int[N], B = new int[N], C = new int[N], D = new int[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            C[i] = Integer.parseInt(st.nextToken());
            D[i] = Integer.parseInt(st.nextToken());
        }

        // 12초 => 약 12억 연산 가능
        // 4000^2 = 1600만 => A+B, C+D의 합을 미리 구해놓고 이분탐색으로 찾기
        int[] AB = new int[N * N];
        int[] CD = new int[N * N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                AB[index] = A[i] + B[j];
                CD[index] = C[i] + D[j];
                index++;
            }
        }

        Arrays.sort(AB);
        Arrays.sort(CD);

        long count = 0;
        for (int i = 0; i < N * N; i++) {
            int target = -AB[i];

            int left = lowerBound(CD, target);
            int right = upperBound(CD, target);
            count += (right - left);
        }

        System.out.println(count);
        br.close();
    }

    // target 이상인 값이 처음 나오는 인덱스 반환
    private static int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // target 초과인 값이 처음 나오는 인덱스 반환
    private static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}
