import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); // 재료의 개수

        int[][] ingredients = new int[n][2]; // 0: 신맛, 1: 쓴맛
        for (int i = 0; i < n; i++) {
            String[] s = br.readLine().split(" ");
            ingredients[i][0] = Integer.parseInt(s[0]);
            ingredients[i][1] = Integer.parseInt(s[1]);
        }

        System.out.println(dfs(ingredients, new boolean[n], 0, 0, 0, 0));

        br.close();
    }

    private static long dfs(int[][] ingredients, boolean[] used, int count, int index, long s, long b) {
        if (index == ingredients.length) {
            if (count != 0) return Math.abs(s - b);
            else return Long.MAX_VALUE; // 무조건 재료는 한 개 이상 사용해야 됨!
        }

        long result = Long.MAX_VALUE;

        // index 번째 재료를 사용하는 경우
        used[index] = true;
        long ns = (s == 0) ? ingredients[index][0] : s *  ingredients[index][0];
        long nb = b + ingredients[index][1];
        result = Math.min(result, dfs(ingredients, used, count + 1, index + 1, ns, nb));

        // index 번째 재료를 사용하지 않는 경우
        used[index] = false;
        result = Math.min(result, dfs(ingredients, used, count , index + 1, s, b));

        return result;
    }
}
