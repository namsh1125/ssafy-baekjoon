import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException { // 시간 제한: 1초, 메모리: 192MB
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) break;

            int N = Integer.parseInt(line);

            String[] words = new String[N];
            for (int i = 0; i < N; i++) {
                words[i] = br.readLine();
            }

            // 단어 삽입
            Tree dictionary = new Tree();
            for (int i = 0; i < N; i++) {
                dictionary.insert(words[i]);
            }

            // 평균 단어 입력 횟수 계산
            int count = 0;
            for (int i = 0; i < N; i++) {
                count += dictionary.countButtonPresses(words[i]);
            }

            double average = (double) count / N;
            sb.append(String.format("%.2f", average)).append("\n");
        }

        System.out.println(sb.toString().trim());
        br.close();
    }

    private static class Tree {
        Node root;

        public Tree() {
            this.root = new Node('\0');
        }

        /**
         * 트리에 단어 삽입하는 메서드
         */
        public void insert(String word) {
            Node node = root;

            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new Node(c);
                    node.childCount++;
                }

                node = node.children[c - 'a'];
            }

            node.isEnd = true;
        }

        /**
         * 단어를 입력하기 위해 눌러야 하는 버튼의 수 계산하는 메서드
         */
        public int countButtonPresses(String word) {
            Node node = root;
            int count = 0;
            char[] w = word.toCharArray();

            for (int i = 0; i < w.length; i++) {
                char c = w[i];

                if (i == 0) count++; // 첫 번째 글자인 경우
                else if (node.childCount > 1) count++; // 추론이 가능하지 않는 경우
                else if (node.isEnd) count++; // 접두사로 끝나는 단어가 있는 경우

                node = node.children[c - 'a'];
            }

            return count;
        }
    }

    private static class Node {
        char c;
        Node[] children; // 영어 소문자로 구성
        int childCount;
        boolean isEnd;

        public Node(char c) {
            this.c = c;
            this.children = new Node[26];
            this.childCount = 0;
            this.isEnd = false;
        }
    }

}
