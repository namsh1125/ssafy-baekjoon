import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long w = Integer.parseInt(st.nextToken()); // 직사각형의 가로 길이
        long h = Integer.parseInt(st.nextToken()); // 직사각형의 세로 길이

        st = new StringTokenizer(br.readLine());
        long p = Integer.parseInt(st.nextToken()); // 초기 위치의 x좌표
        long q = Integer.parseInt(st.nextToken()); // 초기 위치의 y좌표

        int t = Integer.parseInt(br.readLine()); // 시간

        // 처음은 우상으로 시작
        int dx = 1;
        int dy = 1;

        p = (p + t * dx) % (2 * w);
        q = (q + t * dy) % (2 * h);

        if (p > w) p = 2 * w - p;
        if (q > h) q = 2 * h - q;

        System.out.println(p + " " + q);
        br.close();
    }

}
