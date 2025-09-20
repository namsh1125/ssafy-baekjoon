import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] locations = new int[N + 1];
        Set<Integer>[] sets = new HashSet[N + 1];

        for (int i = 1; i <= N; i++) {
            locations[i] = i;
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            int size = Integer.parseInt(st.nextToken());
            sets[i] = new HashSet<>(size);

            while (size-- > 0) {
                sets[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        StringBuilder sb = new StringBuilder();
        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());

            if (st.nextToken().charAt(0) == '1') {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                int la = locations[a], lb = locations[b];
                if (la == lb) continue;

                if (sets[la].size() < sets[lb].size()) {
                    sets[lb].addAll(sets[la]);
                    sets[la] = new HashSet<>();
                    locations[a] = lb;
                    locations[b] = la;

                } else {
                    sets[la].addAll(sets[lb]);
                    sets[lb] = new HashSet<>();
                }

            } else {
                sb.append(sets[locations[Integer.parseInt(st.nextToken())]].size()).append('\n');
            }
        }

        System.out.print(sb);
        br.close();
    }
}
