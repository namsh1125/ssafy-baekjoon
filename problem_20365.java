import java.io.*;
import java.util.*;

public class Main {

    private static final char RED = 'R';
    private static final char BLUE = 'B';

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(br.readLine());

        char[] arr = br.readLine().toCharArray();

        int red = (arr[0] == RED) ? 1 : 0;
        int blue = 1 - red;
        for (int i = 1; i < length; i++) {
            if (arr[i - 1] != arr[i]) {
                if (arr[i] == RED) red++;
                else blue++;
            }
        }

        System.out.println(1 + Math.min(red, blue));
        br.close();
    }

}
