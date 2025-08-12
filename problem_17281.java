import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final int OUT = 0;

    private static int n; // 이닝 수

    private static int[][] record; // i번째 이닝에 j번째 선수가 낼 기록

    private static int score = Integer.MIN_VALUE; // 가장 많은 득점을 할 때의 점수

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        record = new int[n + 1][10];

        for (int ining = 1; ining <= n; ining++) {
            String[] s = br.readLine().split(" ");

            for (int player = 1; player <= 9; player++) {
                record[ining][player] = Integer.parseInt(s[player - 1]);
            }
        }

        permutation(new int[9], 0, new boolean[10]);

        System.out.println(score);
        br.close();
    }

    private static void permutation(int[] order, int depth, boolean[] used) {
        if (depth >= 9) { // 순서가 정해졌다면
            score = Math.max(score, play(order)); // 최대 점수 갱신
            return;
        }

        if (depth == 3) { // 4번째 선수는
            order[depth] = 1; // 1번 선수로 배치
            used[1] = true;
            permutation(order, depth + 1, used);
            return;
        }

        for (int player = 2; player <= 9; player++) { // 1번 선수는 4번째 자리에 이미 존재
            if (!used[player]) {
                used[player] = true;
                order[depth] = player;
                permutation(order, depth + 1, used);
                used[player] = false;
                order[depth] = -1;
            }
        }

    }


    private static int play(int[] order) {
        int index = 0; // 현재 몇번째 타순인지
        int score = 0, ining = 1, outCount = 0;
        boolean[] juja = new boolean[4];

        while (ining <= n) {
            int player = order[index];
            int result = record[ining][player];

            if (result == OUT) outCount++;
            else { // 1루타, 2루타, 3루타, 홈런
                // 점수 업데이트
                for (int i = 3; i >= 1; i--) {
                    if (juja[i]) { // 주자가 있다면
                        if (i + result >= 4) {
                            score++;
                            juja[i] = false;
                        } else {
                            juja[i] = false;
                            juja[i + result] = true;
                        }
                    }
                }

                // 주자 추가
                if (result < 4) juja[result] = true; // 1루타, 2루타, 3루타
                else score++; // 홈런
            }


            if (outCount == 3) {
                Arrays.fill(juja, false); // 주자 초기화
                outCount = 0; // 아웃 카운트 초기화
                ining++; // 다음 이닝으로 넘어감
            }

            index = (index + 1) % 9;
        }

        return score;
    }

}
