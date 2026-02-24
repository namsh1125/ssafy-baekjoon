import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_LIMIT = 10_000_000; // 10^14 까지니까, 소수는 10^7 까지만 구하면 됨

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();

        System.out.println(getResult(A, B));
    }

    private static int getResult(long A, long B) {
        List<Integer> primes = getPrimeNumbers();

        // A ~ B 구간에서의 거의 소수 구하기
        int count = 0;
        for (int prime : primes) {
            long power = (long) prime * prime; // N=2부터 시작
            while (power <= B) {
                if (power >= A) {
                    count++;
                }

                // 다음 제곱으로 넘어가기
                if (power > Long.MAX_VALUE / prime) { // overflow 방지
                    break;
                }

                power *= prime;
            }
        }

        return count;
    }

    private static List<Integer> getPrimeNumbers() {
        boolean[] isPrime = new boolean[MAX_LIMIT + 1];
        Arrays.fill(isPrime, true);

        isPrime[0] = isPrime[1] = false; // 0과 1은 소수가 아님

        for (int i = 2; i <= MAX_LIMIT; i++) {
            for (int j = 2; i * j <= MAX_LIMIT; j++) {
                isPrime[i * j] = false; // i의 배수는 소수가 아님
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= MAX_LIMIT; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

}
