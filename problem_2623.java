import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            graph.put(i, new ArrayList<>());
        }

        int[] inOrder = new int[N + 1];
        while (M-- > 0) {
            int count = sc.nextInt();

            int[] order = new int[count];
            for (int i = 0; i < count; i++) {
                order[i] = sc.nextInt();
            }

            for (int i = 0; i < count - 1; i++) {
                int from = order[i];
                int to = order[i + 1];

                inOrder[to] += 1;
                graph.get(from).add(to);
            }
        }

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            if (inOrder[i] == 0) queue.add(i);
        }

        ArrayList<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int neighbor : graph.get(cur)) {
                inOrder[neighbor]--;
                if (inOrder[neighbor] == 0) queue.add(neighbor);
            }

            order.add(cur);
        }

        if (order.size() != N) System.out.println(0);
        else order.forEach(System.out::println);

        sc.close();
    }

}
