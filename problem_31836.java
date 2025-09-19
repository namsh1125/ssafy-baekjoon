import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        ArrayList<Integer> x = new ArrayList<>(); // 세림이가 받을 기념품의 개수
        ArrayList<Integer> y = new ArrayList<>(); // 성주가 받을 기념품의 개수

        // 항상 연속한 3개의 수는 두 사람에게 똑같이 나누어 줄 수 있다.
        while (N > 1) {
            if (N - 3 >= 0) {
                x.add(N);
                y.add(N - 1);
                y.add(N - 2);;
                N -= 3;
            }

            if (N == 2) {
                x.add(2);
                y.add(1);
                N -= 2;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append(x.size()).append("\n");
        for (int i = 0; i < x.size(); i++) {
            sb.append(x.get(i)).append(" ");
        }

        sb.append(y.size()).append("\n");
        for (int i = 0; i < y.size(); i++) {
            sb.append(y.get(i)).append(" ");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }
}
