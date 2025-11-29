import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 석호촌에 살고 있는 사람의 수
    private static String[] names; // 석호촌에 살고 있는 사람들의 이름

    private static Map<String, Integer> nameToIndex = new HashMap<>(); // Key: 이름, Value: 인덱스

    private static int[] inDegree; // 각 사람의 진입 차수
    private static List<Integer>[] adj; // 조상 -> 후손 관계 그래프
    private static List<Integer>[] children; // 부모 -> 직계 자식 관계 그래프

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        String[] names = new String[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            names[i] = st.nextToken();
        }

        Arrays.sort(names); // 추후 이름의 사전순 대로 출력해야 하기 때문에 미리 정렬
        for (int i = 0; i < N; i++) {
            nameToIndex.put(names[i], i);
        }

        // 초기화
        inDegree = new int[N];
        adj = new ArrayList[N];
        children = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<>();
            children[i] = new ArrayList<>();
        }

        // 조상 정보 입력
        int M = Integer.parseInt(br.readLine()); // 기억하고 있는 정보의 수
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            String descendant = st.nextToken(); // X (후손)
            String ancestor = st.nextToken(); // Y (조상)

            int u = nameToIndex.get(ancestor);
            int v = nameToIndex.get(descendant);

            adj[u].add(v); // 조상 -> 후손
            inDegree[v]++; // 후손의 진입 차수 증가
        }

        // 위상 정렬 수행 및 가문 개수/시조 찾기
        List<String> roots = new ArrayList<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            if (inDegree[i] == 0) {
                roots.add(names[i]);
                queue.offer(i);
            }
        }

        // BFS를 통해 직계 자식 관계 구축
        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int descendant : adj[current]) {
                inDegree[descendant]--;

                if (inDegree[descendant] == 0) {
                    children[current].add(descendant); // 직계 자식 관계 추가
                    queue.offer(descendant);
                }
            }
        }

        // 출력
        StringBuilder sb = new StringBuilder();

        // 가문의 수 출력
        sb.append(roots.size()).append("\n");

        // 시조들의 이름 출력
        for (String root : roots) { // 이미 이름이 사전순으로 정렬되어 있음
            sb.append(root).append(" ");
        }
        sb.append("\n");

        // 각 사람의 자식 정보 출력
        for (int i = 0; i < N; i++) {
            sb.append(names[i]).append(" ").append(children[i].size());

            // 자식 이름들을 사전순으로 정렬하여 출력
            Collections.sort(children[i]);
            for (int child : children[i]) {
                sb.append(" ").append(names[child]);
            }

            sb.append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

}
