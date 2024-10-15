import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class Criptat {
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

		public String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	private static class Word {
		Map<String, Integer> charFrequencyMap;
		int length;
		String dominantLetter;

		public Word(String word) {
			this.length = word.length();
			this.charFrequencyMap = getCharFrequencyMap(word);
			this.dominantLetter = Collections.max(charFrequencyMap.entrySet(),
													Map.Entry.comparingByValue()).getKey();
		}

		/**
		 * @param word the word
		 *
		 * @return hashmap with letters and number of appearances
		 */
		private Map<String, Integer> getCharFrequencyMap(String word) {
			Map<String, Integer> charFrequencyMap = new HashMap<>();
			for (char c : word.toCharArray()) {
				String letter = String.valueOf(c);
				charFrequencyMap.put(letter, charFrequencyMap.getOrDefault(letter, 0) + 1);
			}
			return charFrequencyMap;
		}

		public int getLetterApps(String letter) {
			return charFrequencyMap.getOrDefault(letter, 0);
		}

		public double getLetterFrequency(String letter) {
			return getLetterApps(letter) / (double) length;
		}
	}

	/**
	 * @param words the list of words
	 * @param letter the letter by which we want to sort the words
	 */
	public static void sortWords(List<Word> words, String letter) {
		Collections.sort(words, new Comparator<Word>() {
			@Override
			public int compare(Word w1, Word w2) {
				double freq1 = w1.getLetterFrequency(letter);
				double freq2 = w2.getLetterFrequency(letter);

				if (freq1 > freq2) {
					return -1;
				} else if (freq1 < freq2) {
					return 1;
				} else {
					return Integer.compare(w2.length, w1.length);
				}
			}
		});
	}

	/**
	 * @param words the list of words
	 * @param letter the letter we take from dominantLetters array to try to form
	 *               the biggest password by length
	 *
	 * @return length of the best password formed by sorting words by that letter
	 */
	public static int calculateCurrentLength(List<Word> words, String letter) {
		int totalLength = 0;
		int totalApps = 0;

		for (Word word : words) {
			int newLength = totalLength + word.length;

			int newApps = totalApps + word.getLetterApps(letter);

			if (newApps * 2 > newLength) {
				totalLength = newLength;
				totalApps = newApps;
			}
		}

		return totalLength;
	}

	private static int getMaxLengthPassword(List<Word> words, List<String> dominantLetters) {
		int maxLength = 0;

		for (int i = 0; i < dominantLetters.size(); i++) {
			sortWords(words, dominantLetters.get(i));

			int currentLength = calculateCurrentLength(words, dominantLetters.get(i));

			if (currentLength > maxLength) {
				maxLength = currentLength;
			}
		}

		return maxLength;
	}

	/**
	 * Main.
	 *
	 * @param args the args
	 * @throws IOException the io exception
	 */
	public static void main(final String[] args) throws IOException {
		MyScanner scanner = new MyScanner(new FileReader("criptat.in"));

		int N = scanner.nextInt();

		List<Word> words = new ArrayList<>();
		List<String> dominantLetters = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			Word word = new Word(scanner.nextLine());
			words.add(word);

			String letter = word.dominantLetter;
			if ((double) word.charFrequencyMap.get(word.dominantLetter) / word.length > 0.5) {
				if (!dominantLetters.contains(letter)) {
					dominantLetters.add(letter);
				}
			}
		}

		int result = getMaxLengthPassword(words, dominantLetters);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("criptat.out"))) {
			writer.write(result + "\n");
		}
	}
}
