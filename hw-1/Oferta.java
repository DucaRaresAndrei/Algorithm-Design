import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;


public class Oferta {
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

	private static double calculateSumOf2(double a, double b) {
		if (a > b) {
			return a + b / 2;
		} else {
			return b + a / 2;
		}
	}

	private static double calculateSumOf3(double a, double b, double c) {
		double sum = 0;

		if (a < b) {
			if (c < a) {
				sum = sum + a + b;
			} else {
				sum = sum + b + c;
			}
		} else {
			if (c < b) {
				sum = sum + a + b;
			} else {
				sum = sum + a + c;
			}
		}

		return sum;
	}

	/**
	 * @param N the number of different food
	 * @param P the prices of food
	 *
	 * @return lowest price, using that 2 discounts
	 */
	private static double findLowestPrice(int N, int[] P) {
		double result = 0;

		double min1 = P[0];
		if (N == 1) {
			return min1;
		}

		double min2 = calculateSumOf2(P[0], P[1]);
		if (N == 2) {
			return min2;
		}

		double min3 = Math.min(calculateSumOf3(P[0], P[1], P[2]),
								Math.min(P[2] + min2, calculateSumOf2(P[2], P[1]) + min1));
		if (N == 3) {
			return min3;
		}

		for (int i = 3; i < N; i++) {
			result = Math.min(P[i] + min3,
								Math.min(min2 + calculateSumOf2(P[i], P[i - 1]),
										min1 + calculateSumOf3(P[i], P[i - 1], P[i - 2])));

			min1 = min2;
			min2 = min3;
			min3 = result;
		}

		return result;
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("oferta.in"));

		int N = scanner.nextInt();
		int K = scanner.nextInt();

		int[] P = new int[N];

		for (int i = 0; i < N; i++) {
			P[i] = scanner.nextInt();
		}

		double result = findLowestPrice(N, P);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("oferta.out"))) {
			writer.write(String.format("%.1f", result) + "\n");
		}
	}
}
