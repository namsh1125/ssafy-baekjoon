import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int left = 0, right = 0;
        int answer = 0;
        int[] count = new int[100001]; // 수열의 원소는 최대 100,000까지 가능

        while (right < N) {
            count[arr[right]]++;

            while (count[arr[right]] > K) {
                count[arr[left]]--;
                left++;
            }

            answer = Math.max(answer, right - left + 1);
            right++;
        }

        System.out.println(answer);
        br.close();
    }

}
