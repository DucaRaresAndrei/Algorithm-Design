import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Compresie {
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
	 * @param A first array
	 * @param B second array
	 *
	 * @return maximum length of the new array
	 */
	private static int findLength(List<Integer> A, List<Integer> B) {
		int length = 0;

		int i;
		int j;

		for (i = 0, j = 0;  i < A.size() &&  j < B.size(); i++, j++) {
			if (Objects.equals(A.get(i), B.get(j))) {
				length++;
				continue;
			}

			int xi = i + 1;
			int xj = j + 1;

			while ((A.get(i) > B.get(j) && xj < B.size())
					|| (A.get(i) < B.get(j) && xi < A.size())) {
				if (A.get(i) > B.get(j)) {
					B.set(j, B.get(j) + B.get(xj));
					xj++;
				} else {
					A.set(i, A.get(i) + A.get(xi));
					xi++;
				}
			}

			if (!Objects.equals(A.get(i), B.get(j))) {
				return -1;
			}

			length++;
			i = xi - 1;
			j = xj - 1;
		}

		if (i < A.size() ||  j < B.size()) {
			return -1;
		}

		return length;
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("compresie.in"));

		int n = scanner.nextInt();
		List<Integer> A = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			int aux = scanner.nextInt();
			A.add(aux);
		}

		int m = scanner.nextInt();
		List<Integer> B = new ArrayList<>();

		for (int i = 0; i < m; i++) {
			int aux = scanner.nextInt();
			B.add(aux);
		}

		int result = findLength(A, B);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("compresie.out"))) {
			writer.write(result + "\n");
		}
	}
}
