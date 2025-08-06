import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        List<Integer> result = (n == 1) ? List.of(2, 3, 5, 7) : bfs(n);

        // 출력
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i));
            if (i != result.size() - 1)
                sb.append("\n");
        }
        System.out.println(sb);

        br.close();
    }

    private static List<Integer> bfs(int n) {
        int[] possible = new int[] { 1, 2, 3, 5, 7, 9 }; // 신기한 소수를 만들 수 있는 수들

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(2);
        queue.add(3);
        queue.add(5);
        queue.add(7);

        List<Integer> wowPrimeList = new ArrayList<>();
        while (!queue.isEmpty()) {
            int num = queue.poll();

            if (String.valueOf(num).length() == n) { // n자리의 수인 경우
                if (isPrime(num)) { // 신기한 소수인 경우
                    wowPrimeList.add(num);
                }

                continue;
            }

            // n자리의 수가 아닌 경우
            for (int i = 0; i < possible.length; i++) {
                int nextNum = num * 10 + possible[i];

                if (isPrime(nextNum)) {
                    queue.add(nextNum);
                }
            }
        }

        return wowPrimeList;
    }

    private static boolean isPrime(int num) {
        if (num == 1)
            return false;
        if (num == 2)
            return true;

        for (int i = 2; i <= (int) Math.sqrt(num); i++) {
            if (num % i == 0)
                return false;
        }

        return true;
    }

}
