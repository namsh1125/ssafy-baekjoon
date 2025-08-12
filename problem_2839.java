import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // n이 3과 5로 숫자를 만들 수 있어야 함
        int answer = -1;
        for (int i = n / 5; i >= 0; i--) {
            if ((n - i * 5) % 3== 0) {
                answer = i + (n - i * 5) / 3;
                break;
            }
        }

        System.out.println(answer);
        br.close();
    }

}
