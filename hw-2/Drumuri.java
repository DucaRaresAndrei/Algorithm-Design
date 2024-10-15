import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Drumuri {
	static int N, M;
	static int x, y, z;
	static ArrayList<Pair>[] adj;
	static ArrayList<Pair>[] adjReverse;
	public static final int INF = (int) 1e9;

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
	 * A pair(destination, cost) used for graphs and priority queues
	 */
	public static class Pair implements Comparable<Pair> {
		public int destination;
		public int cost;

		Pair(int _destination, int _cost) {
			destination = _destination;
			cost = _cost;
		}

		public int compareTo(Pair rhs) {
			return Integer.compare(cost, rhs.cost);
		}
	}

	/**
	 * Finds the shortest path distances from a start node
	 *
	 * @param source Starting node
	 * @param adj Graph to search
	 * 
	 * @return an array of shortest path distances from the start node
	 */
	private static List<Integer> dijkstra(int source, ArrayList<Pair>[] adj) {
		List<Integer> d = new ArrayList<>();
		List<Integer> p = new ArrayList<>();

		for (int node = 0; node <= N; node++) {
			d.add(INF);
			p.add(0);
		}

		PriorityQueue<Pair> pq = new PriorityQueue<>();

		d.set(source, 0);
		pq.add(new Pair(source, 0));

		while (!pq.isEmpty()) {
			int cost = pq.peek().cost;
			int node = pq.poll().destination;

			if (cost > d.get(node)) {
				continue;
			}

			for (var e : adj[node]) {
				int neigh = e.destination;
				int w = e.cost;

				if (d.get(node) + w < d.get(neigh)) {
					d.set(neigh, d.get(node) + w);
					p.set(neigh, node);
					pq.add(new Pair(neigh, d.get(neigh)));
				}
			}
		}

		return d;
	}

	/**
	 * Finds the minimum sum of edges required to ensure there are paths from both x and y to z
	 *
	 * @return the minimum sum of edge costs
	 */
	public static long findMinSum() {
		List<Integer> distFromX = dijkstra(x, adj);
		List<Integer> distFromY = dijkstra(y, adj);
		List<Integer> distToZ = dijkstra(z, adjReverse);

		long minCost = Long.MAX_VALUE;
		for (int i = 1; i <= N; i++) {
			if (distFromX.get(i) != INF && distFromY.get(i) != INF && distToZ.get(i) != INF) {
				long totalCost = (long) distFromX.get(i) + distFromY.get(i) + distToZ.get(i);
				if (totalCost < minCost) {
					minCost = totalCost;
				}
			}
		}

		return minCost;
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("drumuri.in"));

		N = scanner.nextInt();
		M = scanner.nextInt();

		adj = new ArrayList[N + 1];
		adjReverse = new ArrayList[N + 1];

		for (int i = 1; i <= N; i++) {
			adj[i] = new ArrayList<>();
			adjReverse[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			int c = scanner.nextInt();

			adj[a].add(new Pair(b, c));
			adjReverse[b].add(new Pair(a, c));
		}

		x = scanner.nextInt();
		y = scanner.nextInt();
		z = scanner.nextInt();

		long result = findMinSum();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("drumuri.out"))) {
			writer.write(result + "\n");
		}
	}
}
