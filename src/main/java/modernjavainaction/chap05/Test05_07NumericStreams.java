package modernjavainaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test05_07NumericStreams {

	private static List<Dish> menu;

	public static void main(String[] args) {
		menu = Arrays.asList(//
				new Dish("pork", false, 800, Dish.Type.MEAT), //
				new Dish("beef", false, 700, Dish.Type.MEAT), //
				new Dish("chicken", false, 400, Dish.Type.MEAT),//
				new Dish("french fries", true, 530, Dish.Type.OTHER),//
				new Dish("rice", true, 350, Dish.Type.OTHER),//
				new Dish("season fruit", true, 120, Dish.Type.OTHER),//
				new Dish("pizza", true, 550, Dish.Type.OTHER),//
				new Dish("prawns", false, 300, Dish.Type.FISH),//
				new Dish("salmon", false, 450, Dish.Type.FISH)//
		);
		List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

		System.out.println("다음처럼 메뉴의 칼로리 합계를 계산할 수 있다");
		// Arrays.stream(numbers.toArray()).forEach(System.out::println);

		System.out.println("숫자 스트림으로 매핑한다");
		int calSum = menu	.stream() // Stream<Dish> 반환
							.mapToInt(Dish::getCalories) // IntStream 반환
							.sum();
		System.out.println("칼로리의 총합 :" + calSum + "\n");

		OptionalDouble calAver = menu	.stream() // Stream<Dish> 반환
										.mapToInt(Dish::getCalories) // IntStream 반환
										.average();
		System.out.println("칼로리의 평균 :" + calAver.getAsDouble() + "\n");

		OptionalInt calMax = menu	.stream() // Stream<Dish> 반환
									.mapToInt(Dish::getCalories) // IntStream 반환
									.max();
		System.out.println("칼로리의 최댓값 :" + calMax.getAsInt() + "\n");

		OptionalInt calMin = menu	.stream() // Stream<Dish> 반환
									.mapToInt(Dish::getCalories) // IntStream 반환
									.min();
		System.out.println("칼로리의 최솟값 :" + calMin.getAsInt() + "\n");

		System.out.println("rangeClosed 가 아니라 range를 사용하게 되면 맨 마지막 숫자는 포함하지 않는다.");
		IntStream evenNumbersInclusive = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
		System.out.println("evenNumbers.count() is : " + evenNumbersInclusive.count());

		IntStream evenNumbersExclusive = IntStream.range(1, 100).filter(n -> n % 2 == 0);
		System.out.println("evenNumbers.count() is : " + evenNumbersExclusive.count());

		// 이 소스는 제곱근을 두 번 계산하므로 최적화가 필요하다
		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
													.boxed()
													.flatMap(a -> IntStream	.rangeClosed(a, 100) //
																			.filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
																			.boxed()//
																			.map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));
		pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

		// 소스는 최적화를 수행하였다.
		Stream<int[]> pythagoreanTriples2 = IntStream	.rangeClosed(1, 100)
														.boxed()
														.flatMap(a -> IntStream	.rangeClosed(a, 100)//
																				.mapToObj(b -> new double[] { a, b, Math.sqrt(a * a + b * b) })//
																				.filter(t -> t[2] % 1 == 0))
														.map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());
		pythagoreanTriples2.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

	}

}
