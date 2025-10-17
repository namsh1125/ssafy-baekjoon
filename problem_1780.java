import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int[][] paper;

    public static int[] count;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        paper = new int[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        count = new int[3];
        parsing(0, 0, N);

        StringBuilder sb = new StringBuilder();
        sb.append(count[0]).append("\n")
                .append(count[1]).append("\n")
                .append(count[2]);

        System.out.println(sb);
        br.close();
    }

    private static void parsing(int i, int j, int length) {
        int color = paper[i][j];
        boolean isSame = true;

        for (int a = i; a < i + length; a++) {
            if (!isSame) break;

            for (int b = j; b < j + length; b++) {
                if (paper[a][b] != color) {
                    isSame = false;
                    break;
                }
            }
        }

        if (isSame) {
            count[color + 1]++; // -1부터 시작하기 때문에 1을 더해서 0부터
            return;
        }

        parsing(i, j, length / 3);
        parsing(i, j + length / 3, length / 3);
        parsing(i, j + 2 * length / 3, length / 3);
        parsing(i + length / 3, j, length / 3);
        parsing(i + length / 3, j + length / 3, length / 3);
        parsing(i + length / 3, j + 2 * length / 3, length / 3);
        parsing(i + 2 * length / 3, j, length / 3);
        parsing(i + 2 * length / 3, j + length / 3, length / 3);
        parsing(i + 2 * length / 3, j + 2 * length / 3, length / 3);
    }

}
