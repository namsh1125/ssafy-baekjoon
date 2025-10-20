import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 문자열의 개수
        int M = Integer.parseInt(st.nextToken()); // 쿼리의 개수

        Trie trie = new Trie();
        for (int i = 0; i < N; i++) {
            trie.insert(br.readLine());
        }

        int count = 0;
        for (int i = 0; i < M; i++) {
            if (trie.isPrefix(br.readLine())) count++;
        }

        System.out.println(count);
        br.close();
    }

    private static class Trie {
        private Node root;

        public Trie() {
            this.root = new Node('\0');
        }

        public void insert(String word) {
            Node current = root;

            for (char ch : word.toCharArray()) {
                int index = ch - 'a';
                if (current.children[index] == null) {
                    current.children[index] = new Node(ch);
                }

                current = current.children[index];
            }

            current.isEnd = true;
        }

        public boolean isPrefix(String word) {
            Node current = root;

            for (char ch : word.toCharArray()) {
                int index = ch - 'a';
                if (current.children[index] == null) return false;
                current = current.children[index];
            }

            return true;
        }

    }

    private static class Node {
        char ch;
        Node[] children;
        boolean isEnd;

        public Node(char ch) {
            this.ch = ch;
            this.children = new Node[26];
            this.isEnd = false;
        }
    }
}
