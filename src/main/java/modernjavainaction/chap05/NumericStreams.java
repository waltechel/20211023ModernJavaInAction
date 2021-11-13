package modernjavainaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStreams {

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

		Arrays.stream(numbers.toArray()).forEach(System.out::println);
		int calories = menu.stream().mapToInt(Dish::getCalories).sum();
		System.out.println("Number of calories:" + calories);

		// max와 OptionalInt
		OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();

		int max;
		if (maxCalories.isPresent()) {
			max = maxCalories.getAsInt();
		} else {
			// 기본값을 선택할 수 있음
			max = 1;
		}
		System.out.println(max);

		// 숫자 범위
		IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
		System.out.println(evenNumbers.count());

		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
													.boxed()
													.flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed().map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));
		pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

		Stream<int[]> pythagoreanTriples2 = IntStream	.rangeClosed(1, 100)
														.boxed()
														.flatMap(a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[] { a, b, Math.sqrt(a * a + b * b) }).filter(t -> t[2] % 1 == 0))
														.map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());
		pythagoreanTriples2.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
	}

	public static boolean isPerfectSquare(int n) {
		return Math.sqrt(n) % 1 == 0;
	}

}
