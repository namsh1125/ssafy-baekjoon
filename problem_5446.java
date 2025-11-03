import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        while (tc-- > 0) {
            int N = Integer.parseInt(br.readLine()); // 지워야 하는 파일의 개수, 1 ≤ N ≤ 1000

            Trie trie = new Trie();
            for (int i = 0; i < N; i++) {
                String fileName = br.readLine();
                trie.insert(fileName, true);
            }

            int M = Integer.parseInt(br.readLine()); // 지우면 안 되는 파일의 개수, 1 ≤ M ≤ 1000
            for (int i = 0; i < M; i++) {
                String fileName = br.readLine();
                trie.insert(fileName, false);
            }

            // 최소 rm 횟수 찾기
            int answer = 0;
            Node root = trie.getRoot();

            ArrayDeque<Node> queue = new ArrayDeque<>();
            if (root.containsFileToErase) {
                queue.add(root);
            }

            while (!queue.isEmpty()) {
                Node current = queue.poll();

                // 1. "rm prefix*" 와일드카드 사용이 가능한 경우
                //  -> 해당 노드(접두사)로 모든 하위 파일이 삭제되므로 더 이상 탐색하지 않음
                if (current.isSafeToDelete && current.containsFileToErase) {
                    answer++;
                    continue;
                }

                // 2. "rm prefix*" 사용이 불가능한 경우 (안전하지 않거나, 지울 파일이 없거나)

                // 2-1. 이 노드 자체가 지워야 할 파일인 경우 ("rm filename")
                if (current.isErasableFile) {
                    answer++;
                }

                // 2-2. 하위 노드 탐색 계속
                for (int i = 0; i < 63; i++) {
                    Node child = current.children[i];

                    if (child != null && child.containsFileToErase) {
                        queue.add(child);
                    }
                }
            }

            sb.append(answer).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static class Trie {
        private Node root;

        public Trie() {
            this.root = new Node();
        }

        public Node getRoot() {
            return this.root;
        }

        public void insert(String word, boolean isErasable) {
            Node node = this.root;

            if (!isErasable) { // 지우면 안 되는 파일인 경우
                node.isSafeToDelete = false;
            } else { // 지워야 하는 파일
                node.containsFileToErase = true;
            }

            for (char c : word.toCharArray()) {
                int index = charToInt(c);

                if (node.children[index] == null) {
                    node.children[index] = new Node();
                }

                if (!isErasable) { // 지우면 안 되는 파일인 경우
                    node.children[index].isSafeToDelete = false;
                } else { // 지워야 하는 파일인 경우
                    node.children[index].containsFileToErase = true;
                }

                node = node.children[index];
            }

            node.isEnd = true;
            if (isErasable) {
                node.isErasableFile = true;
            }
        }

        /**
         * 문자열을 정수로 변환하는 메서드
         * <p>
         * A-Z를 0-25로, a-z를 26-51로, 0-9를 52-61로, 점(.)를 62로 매핑
         * </p>
         *
         * @param c 문자
         * @return 정수 값
         */
        private int charToInt(char c) {
            if (c >= 'A' && c <= 'Z') {
                return c - 'A';
            } else if (c >= 'a' && c <= 'z') {
                return c - 'a' + 26;
            } else if (c >= '0' && c <= '9') {
                return c - '0' + 52;
            } else if (c == '.') {
                return 62;
            }

            return -1; // 예외 처리
        }
    }

    private static class Node {
        Node[] children;
        boolean isEnd;
        boolean isSafeToDelete; // 이 노드를 접두사로 하는 지우면 안 되는 파일이 있는지 여부
        boolean containsFileToErase; // 이 노드를 접두사로 하는 지워야하는 파일이 있는지 여부
        boolean isErasableFile;

        public Node() {
            this.children = new Node[63]; // A-Z, a-z, 0-9, .
            this.isEnd = false;
            this.isSafeToDelete = true;
            this.containsFileToErase = false;
            this.isErasableFile = false;
        }
    }

}
