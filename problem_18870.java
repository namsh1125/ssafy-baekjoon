import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        String[] s = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        List<Integer> list = Arrays.stream(arr)
                .distinct() // 중복된 좌표를 제거하고,
                .sorted() // 순서대로 정렬해서
                .boxed()
                .collect(Collectors.toList()); // 리스트로 모아

        HashMap<Integer, Integer> map = new HashMap<>(); // key: 좌표, value: index
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(map.get(arr[i]));

            if (i != arr.length - 1) sb.append(" ");
        }

        System.out.println(sb);
    }

}
