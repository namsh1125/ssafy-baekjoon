import java.io.*;
import java.util.*;

public class Main {

    private static int N;

    public static void main(String[] args) throws Exception { // 시간 제한: 2초, 메모리 제한: 128MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        boolean[] initialState = new boolean[N];
        String initialStateStr = br.readLine();
        for (int i = 0; i < N; i++) {
            initialState[i] = initialStateStr.charAt(i) == '1';
        }

        boolean[] targetState = new boolean[N];
        String targetStateStr = br.readLine();
        for (int i = 0; i < N; i++) {
            targetState[i] = targetStateStr.charAt(i) == '1';
        }

        int answer = Integer.MAX_VALUE;

        // 시나리오 1: 0번 스위치를 누르지 않는 경우
        boolean[] currentState = Arrays.copyOf(initialState, N);
        int count = 0;
        for (int i = 1; i < N; i++) {
            if (currentState[i - 1] != targetState[i - 1]) {
                toggle(currentState, i);
                count++;
            }
        }

        // 마지막 전구 상태 확인
        if (Arrays.equals(currentState, targetState)) {
            answer = Math.min(answer, count);
        }

        // 시나리오 2: 0번 스위치를 누르는 경우
        currentState = Arrays.copyOf(initialState, N);
        toggle(currentState, 0);
        count = 1;
        for (int i = 1; i < N; i++) {
            if (currentState[i - 1] != targetState[i - 1]) {
                toggle(currentState, i);
                count++;
            }
        }

        // 마지막 전구 상태 확인
        if (Arrays.equals(currentState, targetState)) {
            answer = Math.min(answer, count);
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
        br.close();
    }

    private static void toggle(boolean[] state, int index) {
        for (int i = index - 1; i <= index + 1; i++) {
            if (i >= 0 && i < N) {
                state[i] = !state[i];
            }
        }
    }

}
