import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int maxWeight = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        ArrayList<Marble> marbles = new ArrayList<>(N);
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            marbles.add(new Marble(Integer.parseInt(st.nextToken())));
        }

        for (int i = 1; i < N; i++) {
            marbles.get(i).prev = marbles.get(i - 1);
            marbles.get(i - 1).next = marbles.get(i);
        }

        backTracking(marbles, 0);

        System.out.println(maxWeight);
        br.close();
    }

    private static void backTracking(ArrayList<Marble> marbles, int currentWeight) {
        if (marbles.size() <= 2) {
            maxWeight = Math.max(maxWeight, currentWeight);
            return;
        }

        for (int i = 1; i < marbles.size() - 1; i++) {
            Marble removed = marbles.get(i);
            int addedWeight = removed.prev.weight * removed.next.weight;

            // 구슬 제거
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
            marbles.remove(i);

            backTracking(marbles, currentWeight + addedWeight);

            // 구슬 복원
            marbles.add(i, removed);
            removed.prev.next = removed;
            removed.next.prev = removed;
        }

    }

    private static class Marble {
        int weight;
        Marble prev;
        Marble next;

        Marble(int weight) {
            this.weight = weight;
        }
    }

}
