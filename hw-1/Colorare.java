import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class Colorare {
	static int modulo = 1000000007;

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
	 * @param result the variable where we keep the number of distinct mods of
	 *               coloring
	 * @param exponent the exponent used in our logic
	 * @param power the power exponent needs to be raised
	 *
	 * @return result of adding a pair
	 */
	private static long calculatePower(long result, int exponent, int power) {
		while (Math.sqrt(power) != Math.floor(Math.sqrt(power))) {
			power--;
			result = (result * exponent) % modulo;
		}

		int sqrtPower = (int) Math.sqrt(power);

		long calculatedPower = 1;

		for (int i = 0; i < sqrtPower; i++) {
			calculatedPower = (calculatedPower * exponent) % modulo;
		}

		for (int i = 0; i < sqrtPower; i++) {
			result = (result * calculatedPower) % modulo;
		}

		return result;
	}

	/**
	 * @param result the variable where we keep the number of distinct mods of
	 * 	             coloring
	 * @param x the number of zones
	 * @param T the new type of zone
	 * @param formerType the former zone type
	 *
	 * @return result of adding a pair
	 */
	private static long calculateLogic(long result, int x, String T, int formerType) {
		if (T.equals("H")) {
			if (formerType == 1) {
				result = calculatePower(result, 3, x);
			} else if (formerType == 2) {
				result = (result * 2) % modulo;
				x--;
				result = calculatePower(result, 3, x);
			} else {
				result = 6;
				x--;
				result = calculatePower(result, 3, x);
			}
		} else {
			if (formerType == 1) {
				x--;
				result = calculatePower(result, 2, x);
			} else if (formerType == 2) {
				result = calculatePower(result, 2, x);
			} else {
				result = 3;
				x--;
				result = calculatePower(result, 2, x);
			}
		}

		return result;
	}

	private static long findDistinct(MyScanner scanner, int K) {
		int x;
		String T;
		int formerType = 0;

		long result = 1;

		for (int i = 0; i < K; i++) {
			x = scanner.nextInt();
			T = scanner.next();

			result = calculateLogic(result, x, T, formerType);

			if (T.equals("H")) {
				formerType = 1;
			} else {
				formerType = 2;
			}
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
		MyScanner scanner = new MyScanner(new FileReader("colorare.in"));

		int K = scanner.nextInt();

		long result = findDistinct(scanner, K);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("colorare.out"))) {
			writer.write(result + "\n");
		}
	}
}
