import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(x);

        int l = 1;
        int h = x[N - 1] - x[0];
        int answer = 1;

        while (l <= h) {
            int mid = l + (h - l) / 2;
            if (canPlace(x, C, mid)) { // mid 간격으로 C개 이상 설치 가능 -> 더 키워본다
                answer = mid;
                l = mid + 1;

            } else { // 설치 불가 -> 간격 줄이기
                h = mid - 1;
            }
        }

        System.out.print(answer);
        br.close();
    }

    private static boolean canPlace(int[] x, int C, int dist) {
        int count = 1;
        int last = x[0];

        for (int i = 1; i < x.length; i++) {
            if (x[i] - last >= dist) {
                count++;
                last = x[i];
                if (count >= C) return true;
            }
        }

        return false;
    }

}
