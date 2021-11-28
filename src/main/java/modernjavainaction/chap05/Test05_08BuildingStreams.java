package modernjavainaction.chap05;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test05_08BuildingStreams {

	public static void main(String... args) throws Exception {
		// Stream.of
		System.out.println("5.8.1. 값으로 스트림 만들기");
		Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);

		// Stream.empty
		System.out.println("스트림을 비울 수 있다.");
		Stream<String> emptyStream = Stream.empty();

		System.out.println("5.8.2. null 이 될 수 있는 객체로 스트림 만들기");
		String homeValue = System.getProperty("home");
		Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

		// Arrays.stream
		System.out.println("5.8.3 배열로 스트림 만들기");
		int[] numbers = { 2, 3, 5, 7, 11, 13 };
		System.out.println(Arrays.stream(numbers).sum());

		System.out.println("5.8.4 파일로 스트림 만들기");
		// 스트림은 자원을 자동으로 해제할 수 있는 AutoCloseAble이므로 try-finally 가 필요없다.
		long uniqueWords = Files.lines(Paths.get("src/main/resources/modernjavainaction/chap05/data.txt"), Charset.defaultCharset()) // 
				.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();

		System.out.println("There are " + uniqueWords + " unique words in data.txt");

		System.out.println("5.8.5 함수로 무한 스트림 만들기");
		// Stream.iterate
		Stream.iterate(0, n -> n + 2)
				.limit(10)
				.forEach(System.out::println);

		// iterate를 이용한 피보나치
		System.out.println();
		Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }).limit(10).forEach(t -> System.out.printf("(%d, %d)", t[0], t[1]));

		System.out.println();
		Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] }).limit(10).map(t -> t[0]).forEach(System.out::println);

		// Stream.generate를 이용한 임의의 double 스트림
		System.out.println("generate 메서드 활용하기");
		Stream.generate(Math::random).limit(10).forEach(System.out::println);

		// Stream.generate을 이용한 요소 1을 갖는 스트림
		IntStream.generate(() -> 1).limit(5).forEach(System.out::println);

		IntStream.generate(new IntSupplier() {
			@Override
			public int getAsInt() {
				return 2;
			}
		}).limit(5).forEach(System.out::println);

		IntSupplier fib = new IntSupplier() {

			private int previous = 0;
			private int current = 1;

			@Override
			public int getAsInt() {
				int nextValue = previous + current;
				previous = current;
				current = nextValue;
				return previous;
			}

		};
		IntStream.generate(fib).limit(10).forEach(System.out::println);

	}

}
