package modernjavainaction.chap05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 5.1 필터링
 * @author leedongjun
 *
 */
class Test05_01 {

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

		// 5.1 필터링
		// 5.1.1 프레디케이트 필터링
		System.out.println("5.1.1 프레디케이트 필터링");
		List<Dish> vegetarianMenu = menu.stream()
				.filter(Dish::isVegetarian)
				.collect(Collectors.toList());
		System.out.println(vegetarianMenu);
		System.out.println();

		// 5.1.2 고유 요소 필터링
		System.out.println("5.1.2 고유 요소 필터링");
		List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4, 4, 4, 4, 4, 4);
		numbers.stream()
		.filter(i -> i % 2 == 0) // 중간
		.distinct() // 중간
		.forEach(System.out::println); // 최종
		System.out.println();

	}

}
