import java.io.*;
import java.util.*;

public class Main {

    private static int[] parents; // 부모
    private static int[] ranks; // 랭크(근사 높이)
    private static int[] sizes; // 네트워크 크기
    private static Map<String, Integer> nameToIndexMap;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            int F = Integer.parseInt(br.readLine());
            int N = F * 2; // 최대 등장 인원

            parents = new int[N];
            ranks = new int[N];
            sizes = new int[N];
            nameToIndexMap = new HashMap<>(N * 2);

            for (int i = 0; i < F; i++) {
                String[] input = br.readLine().split(" ");
                int person1 = getIndex(input[0]);
                int person2 = getIndex(input[1]);

                int root = union(person1, person2);   // 랭크 기반 합치기
                sb.append(sizes[root]).append('\n');  // 현재 네트워크 크기
            }
        }

        System.out.print(sb);
        br.close();
    }

    // 처음 보는 이름이면 ID 부여 + 초기화
    private static int getIndex(String name) {
        Integer idx = nameToIndexMap.get(name);
        if (idx == null) {
            idx = nameToIndexMap.size();
            nameToIndexMap.put(name, idx);
            parents[idx] = idx; // 자기 자신이 루트
            ranks[idx] = 0; // 깊이는 0
            sizes[idx] = 1; // 네트워크 크기 1로 시작
        }

        return idx;
    }

    private static int find(int node) {
        if (node != parents[node]) parents[node] = find(parents[node]);
        return parents[node];
    }

    // rank 기반 union + sizes 갱신
    private static int union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return ra;

        // 랭크가 큰 쪽에 랭크가 작은 애를 붙인다.
        // 랭크가 같은 경우 한 쪽에 붙이고 rank를 1 더해준다. (높이가 1 늘어났기 때문)
        if (ranks[ra] < ranks[rb]) {
            parents[ra] = rb;
            sizes[rb] += sizes[ra];
            return rb;

        } else if (ranks[ra] > ranks[rb]) {
            parents[rb] = ra;
            sizes[ra] += sizes[rb];
            return ra;

        } else {
            parents[rb] = ra;
            ranks[ra]++; // 같을 때만 +1
            sizes[ra] += sizes[rb];
            return ra;
        }
    }
}
