import java.io.*;
import java.util.*;

public class Main {

    private static final int ADD_H = 0; // +60분
    private static final int ADD_T = 1; // +10분
    private static final int MIN_T = 2; // -10분
    private static final int ADD_O = 3; // +1분
    private static final int MIN_O = 4; // -1분

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            sb.append(solve(N)).append('\n');
        }

        System.out.print(sb);
    }

    private static String solve(int N) {
        int hour = N / 60;
        int remain = N % 60;

        // case1: hour 유지
        int[] case1 = calc(remain);
        case1[ADD_H] = hour;
        int cnt1 = Arrays.stream(case1).sum();

        // case2: hour+1로 올리고 초과분 음수 처리
        int over = 60 - remain;
        int[] case2 = calc(-over);
        case2[ADD_H] = hour + 1;
        int cnt2 = Arrays.stream(case2).sum();

        // 더 적은 버튼 수 → 같으면 사전순 비교
        if (cnt1 < cnt2 || (cnt1 == cnt2 && isLexicographicallySmaller(case1, case2))) {
            return format(hour, case1);
        } else {
            return format(hour + 1, case2);
        }
    }

    /**
     * 남은 시간 r을 +10, -10, +1, -1 버튼으로 최소 횟수로 조정
     * (나머지 5 이상일 때는 반올림 보정)
     */
    private static int[] calc(int r) {
        int[] res = new int[5]; // [ADDH, ADDT, MINT, ADDO, MINO]

        if (r >= 0) {
            int tens = r / 10;
            int ones = r % 10;

            if (ones <= 5) {
                res[ADD_T] = tens;
                res[ADD_O] = ones;
            } else {
                res[ADD_T] = tens + 1;
                res[MIN_O] = 10 - ones;
            }
            
        } else {
            r = -r;
            int tens = r / 10;
            int ones = r % 10;

            if (ones <= 5) {
                res[MIN_T] = tens;
                res[MIN_O] = ones;
            } else {
                res[MIN_T] = tens + 1;
                res[ADD_O] = 10 - ones;
            }
        }
        
        return res;
    }

    /**
     * 사전순 비교: ADDH → ADDT → MINT → ADDO → MINO 순서로 적은 버튼 수가 앞선다.
     */
    private static boolean isLexicographicallySmaller(int[] a1, int[] a2) {
        for (int i = 0; i < 5; i++) {
            if (a1[i] != a2[i]) return a1[i] < a2[i];
        }
        
        return false;
    }

    private static String format(int h, int[] a) {
        return String.format("%d %d %d %d %d", h, a[ADD_T], a[MIN_T], a[ADD_O], a[MIN_O]);
    }
}
