import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    private static ArrayList<int[]> list = new ArrayList<>();

    private static int n;
    private static int m;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");

        n = Integer.parseInt(s[0]);
        m = Integer.parseInt(s[1]);

        dfs(new int[m], new boolean[n + 1], 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            int[] arr = list.get(i);

            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j]);
                if (j != list.size() - 1)
                    sb.append(" ");
            }

            if (i != list.size() - 1)
                sb.append("\n");
        }

        System.out.println(sb);

        br.close();
    }

    private static void dfs(int[] arr, boolean[] used, int depth) {
        if (depth == m) { // m개를 다 채운경우
            int[] copied = new int[m];
            for (int i = 0; i < m; i++) {
                copied[i] = arr[i];
            }

            list.add(copied);
            return;
        }

        // m개를 채우지 못한 경우
        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                arr[depth] = i;
                used[i] = true;
                dfs(arr, used, depth + 1);

                arr[depth] = -1;
                used[i] = false;
            }
        }

    }

}
