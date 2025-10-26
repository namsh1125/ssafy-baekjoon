import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 소의 수, 4 ≤ N ≤ 200,000
        int Q = Integer.parseInt(st.nextToken()); // 질문의 수, 1 ≤ Q ≤ 200,000

        int[] a = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken()); // 각 소가 가지고 있는 수, 1 ≤ a[i] ≤ 10
        }

        int[] question = new int[Q];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            question[i] = Integer.parseInt(st.nextToken()) - 1; // 장난칠 소의 번호
        }

        long sum = 0;
        long[] prefix = new long[N];
        for (int i = 0; i < N; i++) {
            prefix[i] = a[i] * a[(i + 1) % N] * a[(i + 2) % N] * a[(i + 3) % N];
            sum += prefix[i];
        }

        StringBuilder sb = new StringBuilder();
        for (int q : question) {
            // 장난치기
            for (int i = 0; i < 4; i++) {
                int idx = (q - i + N) % N;
                prefix[idx] = -prefix[idx];
                sum += 2 * prefix[idx];
            }

            sb.append(sum).append('\n');
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

}
