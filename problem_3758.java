import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken()); // 팀의 개수, 3 ≤ n ≤ 100
            int k = Integer.parseInt(st.nextToken()); // 문제의 개수, 3 ≤ k ≤ 100
            int t = Integer.parseInt(st.nextToken()) - 1; // 당신 팀의 ID, 1 ≤ t ≤ n
            int m = Integer.parseInt(st.nextToken()); // 로그 엔트리의 개수, 3 ≤ m ≤ 10,000

            Team[] teams = new Team[n];
            for (int i = 0; i < n; i++) {
                teams[i] = new Team(i, new int[k], 0, 0);
            }

            for (int time = 1; time <= m; time++) {
                st = new StringTokenizer(br.readLine());
                int i = Integer.parseInt(st.nextToken()) - 1; // 팀 ID, 1 ≤ i ≤ n
                int j = Integer.parseInt(st.nextToken()) - 1; // 문제 번호, 1 ≤ j ≤ k
                int s = Integer.parseInt(st.nextToken()); // 획득한 점수, 0 ≤ s ≤ 100

                teams[i].solve(j, s, time);
            }

            Arrays.sort(teams);

            int ranking = 1;
            for (int i = 0; i < n; i++) {
                if (teams[i].index == t) break;
                ranking++;
            }

            sb.append(ranking).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

}

class Team implements Comparable<Team> {
    int index;
    int[] scores;
    int count; // 제출 횟수
    int finalSubmitTime; // 제출 시간

    public Team(int index, int[] scores, int count, int finalSubmitTime) {
        this.index = index;
        this.scores = scores;
        this.count = count;
        this.finalSubmitTime = finalSubmitTime;
    }

    public void solve(int problemId, int score, int submitTime) {
        scores[problemId] = Math.max(scores[problemId], score);
        count++;
        finalSubmitTime = submitTime;
    }

    @Override
    public int compareTo(Team o) {
        int thisSum = Arrays.stream(this.scores).sum();
        int otherSum = Arrays.stream(o.scores).sum();

        if (thisSum != otherSum) return Integer.compare(otherSum, thisSum); // 최종 점수가 높은 순서로
        else if (this.count != o.count) return Integer.compare(this.count, o.count); // 제출 횟수가 적은 순서로
        else return Integer.compare(this.finalSubmitTime, o.finalSubmitTime); // 제출 시간이 빠른 순서로
    }
}
