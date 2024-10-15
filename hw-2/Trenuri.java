import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class Trenuri {
	private static Map<String, List<String>> graph = new HashMap<>();
	private static Map<String, Integer> maxVisitCities = new HashMap<>();

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
	 * Performs topological sorting on the graph
	 *
	 * @return A list representing the topological order of the nodes
	 */
	private static List<String> topologicalSort() {
		Map<String, Integer> inDegree = new HashMap<>();
		Queue<String> queue = new LinkedList<>();
		List<String> topOrder = new ArrayList<>();

		for (String node : graph.keySet()) {
			inDegree.putIfAbsent(node, 0);

			for (String neighbor : graph.get(node)) {
				inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
			}
		}

		for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
			if (entry.getValue() == 0) {
				queue.add(entry.getKey());
			}
		}

		while (!queue.isEmpty()) {
			String current = queue.poll();
			topOrder.add(current);

			for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
				inDegree.put(neighbor, inDegree.get(neighbor) - 1);

				if (inDegree.get(neighbor) == 0) {
					queue.add(neighbor);
				}
			}
		}

		return topOrder;
	}

	/**
	 * Calculates the maximum path using the topological order of the nodes
	 *
	 * @param start First city
	 * @param destination Final city
	 * @param topOrder The topological order of the nodes
	 *
	 * @return The maximum number of distinct cities that can be visited
	 */
	private static int findDistinctCities(String start, String destination, List<String> topOrder) {
		maxVisitCities.put(start, 1);

		for (String node : topOrder) {
			if (!maxVisitCities.containsKey(node)) {
				continue;
			}

			int currentMax = maxVisitCities.get(node);

			for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
				maxVisitCities.put(neighbor, Math.max(maxVisitCities.getOrDefault(neighbor,
						0), currentMax + 1));
			}
		}

		return maxVisitCities.getOrDefault(destination, 0);
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("trenuri.in"));

		String firstCity = scanner.next();
		String finalCity = scanner.next();

		int M = scanner.nextInt();

		for (int i = 0; i < M; i++) {
			String x = scanner.next();
			String y = scanner.next();

			graph.putIfAbsent(x, new ArrayList<>());
			graph.get(x).add(y);
		}

		List<String> topOrder = topologicalSort();
		int result = findDistinctCities(firstCity, finalCity, topOrder);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("trenuri.out"))) {
			writer.write(result + "\n");
		}
	}
}
