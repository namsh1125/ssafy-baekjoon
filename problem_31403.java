import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String A = br.readLine();
        String B = br.readLine();
        String C = br.readLine();

        int result1 = Integer.parseInt(A) + Integer.parseInt(B) - Integer.parseInt(C);
        int result2 = Integer.parseInt(A + B) - Integer.parseInt(C);

        System.out.println(result1);
        System.out.println(result2);
        br.close();
    }

}
