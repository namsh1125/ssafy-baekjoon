import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 3 <= N <= 100,000
        int[] arr = new int[N];
        int[] count = new int[3]; // 1, 2, 3의 개수를 세기 위한 배열

        String[] input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(input[i]);
            count[arr[i] - 1]++;
        }

        int minCount = Integer.MAX_VALUE;

        // 6가지 경우의 수를 계산하여 최소 개수를 찾음
        // 1, 2, 3 순서
        int[] order = getTargetOrder(count, 1, 2, 3);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        // 1, 3, 2 순서
        order = getTargetOrder(count, 1, 3, 2);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        // 2, 1, 3 순서
        order = getTargetOrder(count, 2, 1, 3);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        // 2, 3, 1 순서
        order = getTargetOrder(count, 2, 3, 1);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        // 3, 1, 2 순서
        order = getTargetOrder(count, 3, 1, 2);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        // 3, 2, 1 순서
        order = getTargetOrder(count, 3, 2, 1);
        minCount = Math.min(minCount, getMinSwipingTime(arr, order));

        System.out.println(minCount);
    }

    private static int[] getTargetOrder(int[] count, int first, int second, int third) {
        int[] order = new int[count[0] + count[1] + count[2]];

        for (int i = 0; i < count[first - 1]; i++) order[i] = first;
        for (int i = count[first - 1]; i < count[first - 1] + count[second - 1]; i++) order[i] = second;
        for (int i = count[first - 1] + count[second - 1]; i < order.length; i++) order[i] = third;

        return order;
    }

    private static int getMinSwipingTime(int[] arr, int[] targets) {
        int[][] change = new int[4][4]; // change[i][j] = i에서 j로 바꿔야 하는 개수
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != targets[i]) {
                change[arr[i]][targets[i]]++;
            }
        }

        int count = 0;

        // 1 <-> 2, 1 <-> 3, 2 <-> 3 간의 직접 스와이프를 최대한 활용
        int min12 = Math.min(change[1][2], change[2][1]);
        count += min12; // 1 <-> 2
        change[1][2] -= min12;
        change[2][1] -= min12;

        int min13 = Math.min(change[1][3], change[3][1]);
        count += min13; // 1 <-> 3
        change[1][3] -= min13;
        change[3][1] -= min13;

        int min23 = Math.min(change[2][3], change[3][2]);
        count += min23; // 2 <-> 3
        change[2][3] -= min23;
        change[3][2] -= min23;

        // 남은 변경은 2회 스와이프가 필요하므로 2를 곱함
        count += (change[1][2] + change[1][3]) * 2;

        return count;
    }
}
