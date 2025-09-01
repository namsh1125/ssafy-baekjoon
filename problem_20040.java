import java.io.*;
import java.util.*;

public class Main {

	private static int N;
	private static int M;

	private static int[] parent;

	private static Map<Integer, ArrayList<Integer>> graph;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] s = br.readLine().split(" ");

		N = Integer.parseInt(s[0]);
		M = Integer.parseInt(s[1]);

		graph = new HashMap<>();
		parent = new int[N];
		
		for (int i = 0; i < N; i++) {
			graph.put(i, new ArrayList<>());
			parent[i] = i;
		}

		int count = 0;
		boolean hasCycle = false;
		for (int i = 0; i < M; i++) {
			s = br.readLine().split(" ");
			int a = Integer.parseInt(s[0]);
			int b = Integer.parseInt(s[1]);

			count++;
			if (find(a) != find(b)) {
				union(a, b);

			} else {
				hasCycle = true;
				break;
			}
		}

		System.out.println(hasCycle ? count : 0);

	}

	private static void union(int node1, int node2) {
		int parent1 = find(node1);
		int parent2 = find(node2);

		if (parent1 > parent2)
			parent[parent1] = parent2;
		else
			parent[parent2] = parent1;
	}

	private static int find(int node) {
		if (node == parent[node])
			return node;
		else
			return parent[node] = find(parent[node]);
	}

}
