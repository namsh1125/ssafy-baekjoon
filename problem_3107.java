import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split("::");

        StringBuilder sb = new StringBuilder();
        if (str.length == 0) {
            sb.append("0000:0000:0000:0000:0000:0000:0000:0000");

        } else if (str.length == 1) { // ::가 없는 경우
            String[] s = str[0].split(":");

            for (int i = 0; i < s.length; i++) {
                sb.append("0".repeat(4 - s[i].length())).append(s[i]);
                if (i != s.length - 1) sb.append(":");
            }

            if (s.length != 8) {
                sb.append(":0000".repeat(8 - s.length));
            }

        } else { // ::가 있는 경우
            if (str[0].isEmpty()) {
                String[] s2 = str[1].split(":");

                // 빈 0000 채워넣기
                sb.append("0000:".repeat(8 - s2.length));

                for (int i = 0; i < s2.length; i++) {
                    sb.append("0".repeat(4 - s2[i].length())).append(s2[i]);
                    if (i != s2.length - 1) sb.append(":");
                }

            } else if (str[1].isEmpty()) {
                String[] s1 = str[0].split(":");

                // 빈 0000 채워넣기
                sb.append(":").append("0000:".repeat(8 - s1.length));

                for (int i = 0; i < s1.length; i++) {
                    sb.append("0".repeat(4 - s1[i].length())).append(s1[i]);
                    if (i != s1.length - 1) sb.append(":");
                }

            } else {
                String[] s1 = str[0].split(":");
                String[] s2 = str[1].split(":");

                for (int i = 0; i < s1.length; i++) {
                    sb.append("0".repeat(4 - s1[i].length())).append(s1[i]);
                    if (i != s1.length - 1) sb.append(":");
                }

                // 빈 0000 채워넣기
                sb.append(":").append("0000:".repeat(8 - s1.length - s2.length));

                for (int i = 0; i < s2.length; i++) {
                    sb.append("0".repeat(4 - s2[i].length())).append(s2[i]);
                    if (i != s2.length - 1) sb.append(":");
                }
            }

        }

        System.out.println(sb);
        br.close();
    }

}
