import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static final int A = 0;
    private static final int C = 1;
    private static final int G = 2;
    private static final int T = 3;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");

        int length = Integer.parseInt(s[0]); // 임의로 만든 DNA 문자열 길이
        int p = Integer.parseInt(s[1]); // 비밀번호로 사용할 부분문자열의 길이

        String str = br.readLine(); // 임의의 문자열

        s = br.readLine().split(" ");

        int[] minCount = new int[4]; // 최소 등장 횟수
        for (int i = 0; i < 4; i++) {
            minCount[i] = Integer.parseInt(s[i]);
        }

        // 누적합
        int[][] prefixSum = new int[length + 1][4]; // i: i번째 수까지 j: 부분문자열 ('A', 'C', 'G', 'T')의 사용 횟수
        for (int i = 1; i <= length; i++) {
            // 이전까지 나온 횟수 저장
            System.arraycopy(prefixSum[i - 1], 0, prefixSum[i], 0, 4);

            if (str.charAt(i - 1) == 'A') prefixSum[i][A]++;
            else if (str.charAt(i - 1) == 'C') prefixSum[i][C]++;
            else if (str.charAt(i - 1) == 'G') prefixSum[i][G]++;
            else if (str.charAt(i - 1) == 'T') prefixSum[i][T]++;
        }

        // 슬라이딩 윈도우
        int answer = 0;
        for (int i = 0; i <= length - p; i++) {
            boolean isAvailable = true;
            for (int j = 0; j < 4; j++) {
                if (prefixSum[i + p][j] - prefixSum[i][j] < minCount[j]) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                answer++;
            }
        }

        System.out.println(answer);

    }

}
