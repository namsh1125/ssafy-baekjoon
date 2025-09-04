import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 집의 크기

        StringBuilder sb = new StringBuilder();
        for (int testCase = 1; testCase <= T; testCase++) {
            String[] input = br.readLine().split(" ");
            int R = Integer.parseInt(input[0]);
            int N = Integer.parseInt(input[1]);

            // nCr 계산
            long result = 1;
            for (int i = 0; i < R; i++) {
                result = result * (N - i) / (i + 1);
            }

            sb.append(result);
            if (testCase != T) sb.append("\n");
        }

        System.out.println(sb);
        br.close();
    }

}
