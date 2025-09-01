import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		int[][] map = new int[N][N];
		for (int i = 0; i < N; i++) {
			String[] s = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(s[j]);
			}
		}

		int[] result = dfs(map, 0, 0, N);

		System.out.println(result[0] + "\n" + result[1]);
	}

	private static int[] dfs(int[][] graph, int i, int j, int length) {
		if (length == 1) {
			if (graph[i][j] == 1)
				return new int[] { 0, 1 };
			else
				return new int[] { 1, 0 };
		}
		
		boolean flag = true;
		for (int a = i; a < i + length; a++) {
			for (int b = j; b < j + length; b++) {
				if (graph[i][j] != graph[a][b]) {
					flag = false;
					break;
				}
			}
		}

		if (flag) {
			if (graph[i][j] == 1)
				return new int[] { 0, 1 };
			else
				return new int[] { 1, 0 };

		} else {
			int[] result = new int[2];
			
			int[] space1 = dfs(graph, i, j, length / 2);
			int[] space2 = dfs(graph, i + length / 2, j, length / 2);
			int[] space3 = dfs(graph, i, j + length / 2, length / 2);
			int[] space4 = dfs(graph, i + length / 2, j + length / 2, length / 2);
			
			result[0] += space1[0] + space2[0] + space3[0] + space4[0];
			result[1] += space1[1] + space2[1] + space3[1] + space4[1];
			
			return result;
		}
	}

}
