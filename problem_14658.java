import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");

        int N = Integer.parseInt(input[0]); // 별똥별이 떨어지는 구역의 가로길이, 1 ≤ N ≤ 500,000
        int M = Integer.parseInt(input[1]); // 별똥별이 떨어지는 구역의 세로길이, 1 ≤ M ≤ 500,000
        int L = Integer.parseInt(input[2]); // 트램펄린의 한 변의 길이, 1 ≤ L ≤ 100,000
        int K = Integer.parseInt(input[3]); // 별똥별의 수, 1 ≤ K ≤ 100

        Position[] stars = new Position[K];
        for (int i = 0; i < K; i++) {
            input = br.readLine().split(" ");
            int x = Integer.parseInt(input[0]); // 0 ≤ x ≤ N
            int y = Integer.parseInt(input[1]); // 0 ≤ y ≤ M

            stars[i] = new Position(x, y);
        }

        int maxCount = 0;
        for (int i = 0; i < K; i++) { // i번째 별의 x좌표, j번째 별의 y좌표를 시작점으로 잡아본다.
            for (int j = 0; j < K; j++) {
                int startX = stars[i].x;
                int startY = stars[j].y;

                // (startX, startY)에서 시작하는 L x L 크기의 정사각형 안에 몇 개의 별이 있는지 센다.
                int count = 0;
                for (int k = 0; k < K; k++) {
                    if (startX <= stars[k].x && stars[k].x <= startX + L && startY <= stars[k].y && stars[k].y <= startY + L) {
                        count++;
                    }
                }

                maxCount = Math.max(maxCount, count);
            }
        }

        System.out.println(K - maxCount);
        br.close();
    }

    private static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
