import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            int n = Integer.parseInt(br.readLine());

            Position[] positions = new Position[n + 2]; // 0: 상근이네 집, 1 ~ n: 편의점, n + 1: 페스티벌 좌표
            for (int i = 0; i < n + 2; i++) {
                String[] input = br.readLine().split(" ");
                positions[i] = new Position(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            }

            boolean[][] canReach = new boolean[n + 2][n + 2];
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < n + 2; j++) {
                    canReach[i][j] = positions[i].getDistance(positions[j]) <= 20 * 50; // 20개 * 50m
                }
            }

            // 플로이드 워셜로 도달할 수 있는지 구하기
            for (int k = 0; k < n + 2; k++) {
                for (int i = 0; i < n + 2; i++) {
                    for (int j = 0; j < n + 2; j++) {
                        if (canReach[i][k] && canReach[k][j]) {
                            canReach[i][j] = true;
                        }
                    }
                }
            }

            sb.append(canReach[0][n + 1] ? "happy" : "sad").append("\n");
        }

        System.out.print(sb);
        br.close();
    }

    private static class Position {
        int i, j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getDistance(Position o) {
            return Math.abs(this.i - o.i) + Math.abs(this.j - o.j); // 맨해튼 거리
        }
    }

}
