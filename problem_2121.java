import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        // 데이터 그룹화
        Map<Long, List<Long>> pointMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());

            // x좌표를 Key로, y좌표를 Value 리스트에 추가
            pointMap.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
        }

        // 각 리스트를 y좌표로 정렬
        for (List<Long> list : pointMap.values()) {
            Collections.sort(list);
        }

        // 직사각형으로 만들 수 있는 경우의 수 세기
        long count = 0;
        for (Map.Entry<Long, List<Long>> entry : pointMap.entrySet()) {
            long x1 = entry.getKey();
            List<Long> list1 = entry.getValue();

            long x2 = x1 + A;
            if (!pointMap.containsKey(x2)) continue;
            List<Long> list2 = pointMap.get(x2);

            for (long y1 : list1) {
                long y2 = y1 + B;

                int idx_y2 = Collections.binarySearch(list1, y2);
                if (idx_y2 < 0) continue;

                int idx_y1_in_list2 = Collections.binarySearch(list2, y1);
                if (idx_y1_in_list2 < 0) continue;

                int idx_y2_in_list2 = Collections.binarySearch(list2, y2);
                if (idx_y2_in_list2 < 0) continue;

                count++;
            }
        }

        System.out.println(count);
        br.close();
    }

}
