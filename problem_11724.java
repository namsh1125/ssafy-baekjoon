import java.io.*;
import java.util.*;

public class Main {
	
	private static Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
	
	private static int[] parents;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]); // 서버의 개수
		int m = Integer.parseInt(s[1]); // 서버 간 네트워크 연결 정보 수
		
		for (int i = 1; i <= n; i++) {
			graph.put(i, new ArrayList<>());
		}
		
 		for (int i = 0; i < m; i++) {
			s = br.readLine().split(" ");
			int u = Integer.parseInt(s[0]);
			int v = Integer.parseInt(s[1]);
			
			// 양방향으로 연결
			graph.get(u).add(v);
			graph.get(v).add(u);
		}
 		
 		parents = new int[n + 1];
 		for (int i = 1; i <= n; i++) {
 			parents[i] = i;
 		}
 		
 		for (int i = 1; i <= n; i++) {
 			if (i == parents[i]) {
 				boolean[] visited = new boolean[n + 1];
 				
 				ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
 	 			queue.add(i);
 	 			
 	 			while (!queue.isEmpty()) {
 	 				int cur = queue.poll();
 	 				
 	 				if (visited[cur]) continue;
 	 				visited[cur] = true;
 	 				
 	 				for (int neighbor : graph.get(cur)) {
 	 					union(cur, neighbor);
 	 					queue.add(neighbor);
 	 				}
 	 			}
 			}
 		}
 		
 		Set<Integer> group = new HashSet<>();
 		for (int i = 1; i <= n; i++) {
 			group.add(parents[i]);
 		}
 		
 		System.out.println(group.size());
 
	}
	
	private static void union(int u, int v) {
		int uP = find(u); // u의 부모
		int vP = find(v); // v의 부모
		
		if (uP != vP) {
			parents[Math.max(uP, vP)] = parents[Math.min(uP, vP)]; 
		}
		
	}
	
	private static int find(int node) {
		if (node != parents[node]) {
			parents[node] = find(parents[node]);
		}
		
		return parents[node];
	}

}
