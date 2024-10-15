import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Numarare {
	private static final int MOD = 1000000007;

	private static class MyScanner {
		private BufferedReader br;
		private StringTokenizer st;

		public MyScanner(Reader reader) {
			br = new BufferedReader(reader);
		}

		public String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}
	}

	/**
	 * Initialize a graph
	 *
	 * @param scanner The scanner to read the data
	 * @param M The number of edges
	 * @param graph The graph to initialize
	 */
	private static void readDatas(MyScanner scanner, int M, Map<Integer, List<Integer>> graph) {
		for (int i = 0; i < M; i++) {
			int from = scanner.nextInt();
			int to = scanner.nextInt();

			graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
		}
	}

	/**
	 * Performs topological sorting of the graph
	 *
	 * @param N The number of nodes
	 * @param graph The graph to be sorted
	 *
	 * @return List of topologically sorted nodes
	 */
	private static List<Integer> topologicalSort(int N, Map<Integer, List<Integer>> graph) {
		int[] indegree = new int[N + 1];
		Set<Integer> allNodes = new HashSet<>();
		Queue<Integer> queue = new LinkedList<>();
		List<Integer> sorted = new ArrayList<>();

		for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
			allNodes.add(entry.getKey());

			for (int neighbor : entry.getValue()) {
				indegree[neighbor]++;
				allNodes.add(neighbor);
			}
		}

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0 && allNodes.contains(i)) {
				queue.add(i);
			}
		}

		while (!queue.isEmpty()) {
			int current = queue.poll();
			sorted.add(current);

			for (int child : graph.getOrDefault(current, Collections.emptyList())) {
				indegree[child]--;

				if (indegree[child] == 0) {
					queue.add(child);
				}
			}
		}

		return sorted;
	}

	/**
	 * Count the common paths from node 1 to node N in the two graphs.
	 *
	 * @param N The number of nodes
	 * @param graph1 First graph
	 * @param graph2 Second graph
	 *
	 * @return Number of common paths
	 */
	private static int findElementaryChains(int N, Map<Integer, List<Integer>> graph1,
											Map<Integer, List<Integer>> graph2) {
		int[] dp = new int[N + 1];
		dp[1] = 1;

		Map<Integer, List<Integer>> combinedGraph = new HashMap<>();

		for (int node : graph1.keySet()) {
			combinedGraph.putIfAbsent(node, new ArrayList<>(graph1.get(node)));
			combinedGraph.get(node).addAll(graph1.get(node));
		}

		for (int node : graph2.keySet()) {
			combinedGraph.putIfAbsent(node, new ArrayList<>(graph2.get(node)));
			combinedGraph.get(node).addAll(graph2.get(node));
		}

		List<Integer> sortedNodes = topologicalSort(N, combinedGraph);

		for (int node : sortedNodes) {
			if (dp[node] > 0 && graph1.containsKey(node) && graph2.containsKey(node)) {
				List<Integer> children1 = graph1.get(node);
				List<Integer> children2 = graph2.get(node);

				Set<Integer> commonChildren = new HashSet<>(children1);
				commonChildren.retainAll(children2);

				for (int child : commonChildren) {
					dp[child] = (dp[child] + dp[node]) % MOD;
				}
			}
		}

		return dp[N];
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("numarare.in"));

		int N = scanner.nextInt();
		int M = scanner.nextInt();

		Map<Integer, List<Integer>> graph1 = new HashMap<>();
		Map<Integer, List<Integer>> graph2 = new HashMap<>();

		readDatas(scanner, M, graph1);
		readDatas(scanner, M, graph2);

		int result = findElementaryChains(N, graph1, graph2);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("numarare.out"))) {
			writer.write(result + "\n");
		}
	}
}
