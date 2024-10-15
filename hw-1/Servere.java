import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Servere {
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
	 * @param N the number of servers
	 * @param P the powers
	 * @param C the limits
	 *
	 * @return max power of the system
	 */
	private static double findMaxPower(int N, int[] P, int[] C) {
		double left = Arrays.stream(C).min().getAsInt();
		double right = Arrays.stream(C).max().getAsInt();

		double maxPower = -999999999;

		while (left <= right) {
			double mid = (right + left) / 2;

			if (!(Math.floor(mid * 10) == mid * 10 && Math.floor(mid * 10) % 10 == 5)) {
				mid = Math.round(mid);
			}

			double midPower = Double.MAX_VALUE;
			double leftPower = Double.MAX_VALUE;
			double rightPower = Double.MAX_VALUE;

			for (int i = 0; i < N; i++) {
				midPower = Math.min(midPower, P[i] - Math.abs(mid - C[i]));
				leftPower = Math.min(leftPower, P[i] - Math.abs(mid - 0.5 - C[i]));
				rightPower = Math.min(rightPower, P[i] - Math.abs(mid + 0.5 - C[i]));
			}

			if (midPower > maxPower) {
				maxPower = midPower;
			}

			if (leftPower < midPower) {
				if (rightPower > maxPower) {
					maxPower = rightPower;
				}

				left = mid + 0.5;
			} else {
				if (leftPower > maxPower) {
					maxPower = leftPower;
				}

				right = mid - 0.5;
			}
		}

		return maxPower;
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("servere.in"));

		int N = scanner.nextInt();

		int[] P = new int[N];
		int[] C = new int[N];

		for (int i = 0; i < N; i++) {
			P[i] = scanner.nextInt();
		}

		for (int i = 0; i < N; i++) {
			C[i] = scanner.nextInt();
		}

		double result = findMaxPower(N, P, C);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("servere.out"))) {
			writer.write(String.format("%.1f", result) + "\n");
		}
	}	
}
