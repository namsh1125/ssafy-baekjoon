import java.io.*;
import java.util.*;

public class Main {

    private static int N;
    private static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 배열의 길이, 2 ≤ N ≤ 8
        List<Integer> startList = new ArrayList<>(N);

        // 시작 배열 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            startList.add(Integer.parseInt(st.nextToken())); // 배열의 요소, 1 ≤ startList.get(i) ≤ 10
        }

        int M = Integer.parseInt(br.readLine());
        List<Operation> operations = new ArrayList<>(M);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken()) - 1; // 0-based index로 변환
            int v = Integer.parseInt(st.nextToken()) - 1; // 0-based index로 변환
            int weight = Integer.parseInt(st.nextToken());

            operations.add(new Operation(u, v, weight));
        }

        // 목표 상태 생성
        List<Integer> targetList = new ArrayList<>(startList);
        Collections.sort(targetList);

        int result = dijkstra(startList, targetList, operations);
        System.out.println(result);

        br.close();
    }

    private static int dijkstra(List<Integer> startList, List<Integer> targetList, List<Operation> operations) {
        Map<List<Integer>, Integer> costMap = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        costMap.put(startList, 0);
        pq.offer(new Node(startList, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            List<Integer> currentList = current.currentList;
            int currentCost = current.cost;

            // 현재 비용이 이미 기록된 비용보다 크면 무시
            if (costMap.containsKey(currentList) && costMap.get(currentList) < currentCost) {
                continue;
            }

            // 목표 상태에 도달했는지 확인
            if (currentList.equals(targetList)) {
                return currentCost;
            }

            // 가능한 모든 조작 시도
            for (Operation op : operations) {
                List<Integer> newList = new ArrayList<>(currentList);

                // 두 위치의 값을 교환
                Collections.swap(newList, op.u, op.v);
                int newCost = currentCost + op.weight;

                // 새로운 상태에 대한 비용이 더 작으면 갱신
                if (!costMap.containsKey(newList) || newCost < costMap.get(newList)) {
                    costMap.put(newList, newCost);
                    pq.offer(new Node(newList, newCost));
                }
            }
        }

        return -1; // 목표 상태에 도달할 수 없는 경우
    }

    private static class Node implements Comparable<Node> {
        List<Integer> currentList;
        int cost;

        public Node(List<Integer> currentList, int cost) {
            this.currentList = currentList;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    private static class Operation {
        int u, v;
        int weight;

        public Operation(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

}
