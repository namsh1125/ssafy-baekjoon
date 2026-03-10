import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static final int[] di = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dj = {-1, 0, 1, -1, 1, -1, 0, 1};

    private static int N;
    private static char[][] arr;
    private static boolean[][] isExist;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new char[N][N];
        isExist = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < N; j++) {
                arr[i][j] = str.charAt(j);
            }
        }

        // 가장자리의 안쪽에 폭탄이 있는지 확인
        // arr[0][0]이 1이면 arr[1][1]은 무조건 폭탄이 있으며 arr[0][0]이 0이면 arr[1][1]은 폭탄이 없다.

        // 가장자리의 모서리에 지뢰가 있는지 확인
        if (arr[0][0] == '1') {
            markMineExist(1, 1);
        }

        if (arr[0][N - 1] == '1') {
            markMineExist(1, N - 2);
        }

        if (arr[N - 1][0] == '1') {
            markMineExist(N - 2, 1);
        }

        if (arr[N - 1][N - 1] == '1') {
            markMineExist(N - 2, N - 2);
        }

        // 가장자리의 변에 지뢰가 있는지 확인
        for (int j = 1; j < N; j++) {
            if (arr[0][j] == '1') {
                markMineExist(1, j + 1);
            }
        }

        for (int i = 1; i < N; i++) {
            if (arr[i][0] == '1') {
                markMineExist(i + 1, 1);
            }
        }

        for (int i = 1; i < N; i++) {
            if (arr[i][N - 1] == '1') {
                markMineExist(i + 1, N - 2);
            }
        }

        for (int j = 1; j < N; j++) {
            if (arr[N - 1][j] == '1') {
                markMineExist(N - 2, j + 1);
            }
        }

        // 가장자리 안쪽의 안에는 폭탄이 있다고 가정 -> 그래야 최대 폭탄의 수를 구할 수 있다.
        int count = 0;
        if (N >= 5) {
            count += (N - 4) * (N - 4);
        }

        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < N - 1; j++) {
                if (isExist[i][j]) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    private static void markMineExist(int i, int j) {
        isExist[i][j] = true;

        for (int dir = 0; dir < 8; dir++) {
            int ni = i + di[dir];
            int nj = j + dj[dir];

            if (isInRange(ni, nj) && Character.isDigit(arr[ni][nj])) {
                arr[ni][nj] = (char) (arr[ni][nj] - 1);
                isExist[ni][nj] = true;
            }
        }
    }

    private static boolean isInRange(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }

}
