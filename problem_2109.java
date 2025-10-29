import java.io.*;
import java.util.*;

public class Main {

    private static final int MAX_DAY = 10000;

    private static Map<Integer, List<Integer>> teleport;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        PriorityQueue<Lecture> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());

            queue.add(new Lecture(left, cost));
        }

        int money = 0;
        boolean[] isImpossible = new boolean[MAX_DAY + 1];
        while (!queue.isEmpty()) {
            Lecture lecture = queue.poll();

            for (int day = lecture.left; day >= 1; day--) {
                if (!isImpossible[day]) {
                    money += lecture.money;
                    isImpossible[day] = true;
                    break;
                }
            }

        }

        System.out.println(money);
        br.close();
    }

    private static class Lecture implements Comparable<Lecture> {
        int left; // 제한시간
        int money; // 강연료

        public Lecture(int left, int money) {
            this.left = left;
            this.money = money;
        }

        @Override
        public int compareTo(Lecture o) {
            if (this.money == o.money) {
                return Integer.compare(this.left, o.left); // 제한시간이 촉박할수록
            }

            return Integer.compare(o.money, this.money); // 강연료가 많은 순으로
        }
    }

}
