import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static Node[] nodes;
    private static List<Integer>[] levels;
    private static int traversalCount = 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 노드의 개수, 1 ≤ N ≤ 10,000

        nodes = new Node[N + 1];
        for (int i = 0; i <= N; i++) {
            nodes[i] = new Node(i);
        }

        ArrayDeque<Node> queue = new ArrayDeque<>(); // 루트 노드 찾기용 큐
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int leftId = Integer.parseInt(st.nextToken());
            int rightId = Integer.parseInt(st.nextToken());

            if (leftId != -1) {
                nodes[id].left = nodes[leftId];
                nodes[leftId].parent = nodes[id];
            }

            if (rightId != -1) {
                nodes[id].right = nodes[rightId];
                nodes[rightId].parent = nodes[id];
            }

            if (leftId == -1 && rightId == -1) { // 단말 노드인 경우
                queue.offer(nodes[id]);
            }
        }

        // 루트 노드 찾기
        Node root = queue.peek();
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.parent == null) {
                root = current;
                break;
            }

            queue.offer(current.parent);
        }

        // 레벨별 노드의 열 번호를 저장할 리스트 초기화
        levels = new ArrayList[N + 1];
        for (int i = 0; i <= N; i++) {
            levels[i] = new ArrayList<>();
        }

        inOrderTraversal(root, 1);

        int maxWidthDepth = 0, maxWidth = 0;
        for (int depth = 1; depth <= N; depth++) {
            if (levels[depth].isEmpty()) break;

            int width = levels[depth].get(levels[depth].size() - 1) - levels[depth].get(0) + 1;
            if (width > maxWidth) {
                maxWidth = width;
                maxWidthDepth = depth;
            }
        }

        System.out.println(maxWidthDepth + " " + maxWidth);
        br.close();
    }

    private static void inOrderTraversal(Node node, int depth) {
        if (node.left != null) {
            inOrderTraversal(node.left, depth + 1);
        }

        levels[depth].add(traversalCount++);

        if (node.right != null) {
            inOrderTraversal(node.right, depth + 1);
        }
    }

    private static class Node {
        int id;
        Node parent;
        Node left;
        Node right;

        public Node(int id) {
            this.id = id;
            this.parent = null;
            this.left = null;
            this.right = null;
        }
    }

}
