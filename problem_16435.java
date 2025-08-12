import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]); // 과일의 개수
        int length = Integer.parseInt(s[1]); // 초기 길이 정수

        int[] arr = new int[n];
        s = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        Arrays.sort(arr);

        for (int i = 0; i < n; i++) {
            if (arr[i] <= length) length++;
            else break;
        }

        System.out.println(length);
        br.close();
    }

}
