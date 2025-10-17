import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception { // 시간 제한: 3초, 메모리 제한: 1024MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken()); // 색상의 종류, 1 ≤ C ≤ 4,000
        int N = Integer.parseInt(st.nextToken()); // 닉네임의 개수, 1 ≤ N ≤ 4,000

        // 색상 정보 입력
        Trie trie = new Trie();
        for (int i = 0; i < C; i++) {
            trie.insert(br.readLine());
        }

        // 닉네임 정보 입력
        Set<String> nicknames = new HashSet<>();
        for (int i = 0; i < N; i++) {
            nicknames.add(br.readLine());
        }

        // 쿼리 처리
        int Q = Integer.parseInt(br.readLine()); // 팀의 개수, 1 ≤ Q ≤ 20,000
        boolean[] isAwardable = new boolean[Q];
        for (int i = 0; i < Q; i++) {
            char[] query = br.readLine().toCharArray();

            Node current = trie.root;
            for (int j = 0; j < query.length; j++) {
                // 색상 조회
                int index = query[j] - 'a';
                if (current.children[index] == null) {
                    break;
                }

                current = current.children[index];

                if (current.isEndOfWord) {
                    // 닉네임 조회
                    String nickname = new String(query, j + 1, query.length - j - 1);
                    if (nicknames.contains(nickname)) {
                        isAwardable[i] = true;
                        break;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (boolean awardable : isAwardable) {
            sb.append(awardable ? "Yes\n" : "No\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static class Trie {
        private Node root;

        public Trie() {
            root = new Node(new Node[26], false);
        }

        public void insert(String word) {
            Node current = root;

            for (char ch : word.toCharArray()) {
                int index = ch - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new Node(ch, new Node[26], false);
                }

                current = current.children[index];
            }

            current.isEndOfWord = true;
        }
    }

    private static class Node {
        char ch;
        Node[] children;
        boolean isEndOfWord;

        public Node(Node[] children, boolean isEndOfWord) {
            this.children = children;
            this.isEndOfWord = isEndOfWord;
        }

        public Node(char ch, Node[] children, boolean isEndOfWord) {
            this.ch = ch;
            this.children = children;
            this.isEndOfWord = isEndOfWord;
        }
    }

}
