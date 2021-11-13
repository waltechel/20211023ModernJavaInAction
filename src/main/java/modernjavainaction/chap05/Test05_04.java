package modernjavainaction.chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 5.4 검색과 매칭
 * @author leedongjun
 *
 */
class Test05_04 {

	private static List<Dish> menu;

	public static void main(String... args) {

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

		// anyMatch는 불리언을 반환하므로 최종 연산이다
		System.out.println("5.4.1 프레디케이트가 적어도 한 요소와 일치하는지 확인");
		if (menu.stream().anyMatch(Dish::isVegetarian)) {
			System.out.println("menu is any vegetarian friendly!");
		}
		System.out.println();

		System.out.println("5.4.2 프레디케이트가 모든 요소와 일치하는지 검사");
		if (menu.stream().allMatch(Dish::isVegetarian)) {
			System.out.println("Menus are all vegetarian friendly!");
		} else {
			System.out.println("Not all menus are for vegetarians.");
		}
		System.out.println();

		if (menu.stream().noneMatch(Dish::isVegetarian)) {
			System.out.println("No menu is vegetarian friendly!");
		} else {
			System.out.println("Some menus are for vegetarians.");
		}
		System.out.println();

		// Optional이란? 다음 소스는 값이 있으면 검색되고 값이 없으면 출력되지 않는다.
		System.out.println("5.4.3 요소 검색");
		Optional<Dish> dish = findAnyVegetarianDish();
		dish.ifPresent(d -> System.out.println(d.getName()));
		System.out.println();
		
		System.out.println("5.4.4 첫 번째 요소");
		List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
		// NullPointerException을 피하게 해줄 수 있는 Optional
		Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
				.map(x -> x * x)
				.filter(x -> x % 3 == 0)
				.findFirst();
		firstSquareDivisibleByThree.ifPresent(number -> System.out.println(number));
	}

	private static Optional<Dish> findAnyVegetarianDish() {
		return menu.stream().filter(Dish::isVegetarian).findAny();
	}

}
