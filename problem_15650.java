import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }

        combination(arr, m, new ArrayDeque<>(), 0);

        br.close();
    }

    private static void combination(int[] arr, int m, ArrayDeque<Integer> selected, int index) {
        if (selected.size() == m) { // m개를 선택한 경우
            StringBuilder sb = new StringBuilder();
            selected.forEach(num -> sb.append(num).append(" "));
            System.out.println(sb);

            return;
        }

        if (index >= arr.length) {
            return;
        }

        // i번째 인덱스를 고른 경우
        selected.add(arr[index]);
        combination(arr, m, selected, index + 1);

        // i번째 인덱스를 고르지 않은 경우
        selected.removeLast();
        combination(arr, m, selected, index + 1);
    }

}
