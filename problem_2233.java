import java.io.*;
import java.util.*;

public class Main {

    private static int N; // 정점의 개수, 1 ≤ N ≤ 2,000

    private static Tree tree;
    private static Node[] nodes;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 처리
        N = Integer.parseInt(br.readLine());
        char[] traversal = br.readLine().toCharArray();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken()) - 1;
        int Y = Integer.parseInt(st.nextToken()) - 1;

        // 노드 배열 초기화
        nodes = new Node[N + 1];
        Node node = new Node(0, 0, null); // 루트 노드
        nodes[0] = node;

        // 트리 생성
        tree = new Tree(node);

        // traversal의 i번째 문자가 가리키는 노드를 추적하는 배열
        int[] traversalNodeIds = new int[traversal.length];

        int nodeId = 1;
        for (int i = 0; i < traversal.length; i++) {
            char c = traversal[i];

            if (c == '0') { // 새로운 노드 방문
                Node newNode = new Node(nodeId, node.depth + 1, node);
                nodes[nodeId] = newNode;
                nodeId++;

                node.children.add(newNode);
                node = newNode;
            }

            // 현재 traversal 위치의 노드 id 저장
            traversalNodeIds[i] = node.id;

            if (c == '1') { // 모든 자식을 방문하고 리턴
                node = node.parent;
            }
        }

        // 썩은 사과 노드
        Node rottenAppleX = nodes[traversalNodeIds[X]];
        Node rottenAppleY = nodes[traversalNodeIds[Y]];

        // 최소 공통 조상(LCA) 찾기
        while (rottenAppleX.depth != rottenAppleY.depth) {
            if (rottenAppleX.depth > rottenAppleY.depth) {
                rottenAppleX = rottenAppleX.parent;
            } else {
                rottenAppleY = rottenAppleY.parent;
            }
        }

        while (rottenAppleX != rottenAppleY) {
            rottenAppleX = rottenAppleX.parent;
            rottenAppleY = rottenAppleY.parent;
        }

        // LCA 노드의 id를 이용해 i, j 계산
        int i = -1, j = -1;
        for (int index = 0; index < traversalNodeIds.length; index++) {
            if (traversalNodeIds[index] == rottenAppleX.id) { // LCA 노드 발견
                if (i == -1) {
                    i = index + 1;
                } else {
                    j = index + 1;
                    break;
                }
            }
        }

        System.out.println(i + " " + j);
        br.close();
    }


    private static class Tree {
        Node root;

        public Tree(Node root) {
            this.root = root;
        }
    }

    private static class Node {
        int id;
        List<Node> children;
        Node parent;
        int depth;

        public Node(int id, int depth, Node parent) {
            this.id = id;
            this.depth = depth;
            this.children = new ArrayList<>();
            this.parent = parent;
        }
    }

}
