import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] length = new int[N];
        for (int i = 0; i < N; i++) {
            length[i] = Integer.parseInt(br.readLine());
        }

        int totalSum = Arrays.stream(length).sum(); // 전체 거리의 총합 <= 10억

        // 투 포인터로, 두 탑의 거리가 최대가 되는 값 구하기
        int left = N - 1;
        int right = N - 1;
        int distance = 0;
        int answer = 0;
        while (left >= 0) {
            int anotherDistance = totalSum - distance;
            answer = Math.max(answer, Math.min(distance, anotherDistance));

            if (distance <= anotherDistance) {
                distance += length[left];
                left--;

            } else {
                distance -= length[right];
                right--;
            }

        }

        System.out.print(answer);
        br.close();
    }

}
