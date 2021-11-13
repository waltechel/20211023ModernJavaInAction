package modernjavainaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 5.5 리듀싱
 * @author leedongjun
 *
 */
public class Test05_05Reducing {

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

		// 5.5.1 요소의 합
		// 초깃값은 0, 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator<T>
		System.out.println("5.5.1 요소의 합");
		List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
		int sum1 = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println(sum1 + "\n");

		int sum2 = numbers.stream().reduce(0, Integer::sum);
		System.out.println(sum2 + "\n");

		Optional<Integer> sum3 = numbers.stream().reduce(Integer::sum);
		sum3.ifPresent(n -> System.out.println(n + "\n"));

		int product1 = numbers.stream().reduce(1, (a, b) -> a * b);
		System.out.println(product1 + "\n");

		// 5.5.2 최댓값과 최솟값
		int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
		System.out.println(max + "\n");

		Optional<Integer> min = numbers.stream().reduce(Integer::min);
		min.ifPresent(n -> System.out.println(n + "\n"));

		int calories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
		System.out.println("Number of calories:" + calories);
	}

}
