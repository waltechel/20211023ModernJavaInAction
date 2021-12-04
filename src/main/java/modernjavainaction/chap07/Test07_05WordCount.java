package modernjavainaction.chap07;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Spliterator;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Test07_05WordCount {
	
	private static String SENTENCE1, SENTENCE2;
	
	public static void main(String[] args) throws Exception {
		
		System.gc(); 
		long before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		BufferedReader br;

		br = new BufferedReader(new FileReader("src/main/java/modernjavainaction/chap07/sampleText.txt"));
		
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < 2_000_000; i++) {
			String line = br.readLine();
			if (line == null || "".equals(line)) {
				continue;
			}
			sb1.append(line);
		}
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < 2_000_000; i++) {
			String line = br.readLine();
			if (line == null || "".equals(line)) {
				continue;
			}
			sb2.append(line);
		}
		
		// 실행 후 메모리 사용량 조회
		long after  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				
		// 메모리 사용량 측정
		long usedMemory = (after - before)/1024/1024;
		System.out.println("before : " + before + ", after : " + after + ", Used Memory : " + usedMemory);
		
		SENTENCE1 = sb1.toString();
		SENTENCE2 = sb2.toString();
		
		long start = System.nanoTime();
		System.out.println("Found " + countWordsIterativelyHandmade(SENTENCE1) + " words");		
		System.out.println("Found " + countWordsIterativelyHandmade(SENTENCE2) + " words");		
		System.out.println("countWordsIterativelyHandmade Result: " + (System.nanoTime() - start) / 1_000_000);
		
		start = System.nanoTime();
		System.out.println("Found " + countWordsIteratively(SENTENCE1) + " words");
		System.out.println("Found " + countWordsIteratively(SENTENCE2) + " words");
		System.out.println("countWordsIteratively Result: " + (System.nanoTime() - start) / 1_000_000);
		
		start = System.nanoTime();
		System.out.println("Found " + countWords(SENTENCE1) + " words");
		System.out.println("Found " + countWords(SENTENCE2) + " words");
		System.out.println("countWords Result: " + (System.nanoTime() - start) / 1_000_000);
		
		Spliterator<Character> spliterator1 = new WordCounterSpliterator(SENTENCE1);
		Stream<Character> stream1 = StreamSupport.stream(spliterator1, true);
		Spliterator<Character> spliterator2 = new WordCounterSpliterator(SENTENCE2);
		Stream<Character> stream2 = StreamSupport.stream(spliterator2, true);
		start = System.nanoTime();
		System.out.println("Found " + countWords(stream1) + " words");
		System.out.println("Found " + countWords(stream2) + " words");
		System.out.println("countWords(분할 정복) Result: " + (System.nanoTime() - start) / 1_000_000);
	}

	private static int countWordsIterativelyHandmade(String s) {
		int ret = 0;
		StringTokenizer st = new StringTokenizer(s);
		while(st.hasMoreTokens()) {
			st.nextToken();
			ret++;
		}
		return ret;
	}

	/**
	 * 반복형으로 단어 수를 세는 메서드
	 * @param s
	 * @return
	 */
	public static int countWordsIteratively(String s) {
		int counter = 0;
		boolean lastSpace = true;
		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				lastSpace = true;
			} else {
				if (lastSpace) {
					counter++;
				}
				lastSpace = Character.isWhitespace(c);
			}
		}
		return counter;
	}

	public static int countWords(String s) {
		// Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(SENTENCE::charAt).parallel();
		Spliterator<Character> spliterator = new WordCounterSpliterator(s);
		Stream<Character> stream = StreamSupport.stream(spliterator, true);

		return countWords(stream);
	}

	private static int countWords(Stream<Character> stream) {
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		return wordCounter.getCounter();
	}

	private static class WordCounter {

		private final int counter;
		private final boolean lastSpace;

		public WordCounter(int counter, boolean lastSpace) {
			this.counter = counter;
			this.lastSpace = lastSpace;
		}

		public WordCounter accumulate(Character c) {
			if (Character.isWhitespace(c)) {
				return lastSpace ? this : new WordCounter(counter, true);
			} else {
				return lastSpace ? new WordCounter(counter + 1, false) : this;
			}
		}

		public WordCounter combine(WordCounter wordCounter) {
			return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
		}

		public int getCounter() {
			return counter;
		}

	}

	private static class WordCounterSpliterator implements Spliterator<Character> {

		private final String string;
		private int currentChar = 0;

		private WordCounterSpliterator(String string) {
			this.string = string;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			action.accept(string.charAt(currentChar++));
			return currentChar < string.length();
		}

		@Override
		public Spliterator<Character> trySplit() {
			int currentSize = string.length() - currentChar;
			if (currentSize < 10) {
				return null;
			}
			for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
				if (Character.isWhitespace(string.charAt(splitPos))) {
					Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
					currentChar = splitPos;
					return spliterator;
				}
			}
			return null;
		}

		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}

		@Override
		public int characteristics() {
			return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
		}

	}

}
