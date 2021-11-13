package modernjavainaction.chap05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 5.2 스트림 슬라이싱
 * @author leedongjun
 *
 */
class Test05_02 {
	
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

		// 5.2 스트림 슬라이싱
		// 이 데이터는 칼로리 순으로 정렬되어 있지 않다!
		List<Dish> specialMenu = Arrays.asList(//
				new Dish("season fruit", true, 120, Dish.Type.OTHER),//
				new Dish("rice", true, 350, Dish.Type.OTHER),//
				new Dish("prawns", false, 300, Dish.Type.FISH),//
				new Dish("chicken", false, 400, Dish.Type.MEAT),//
				new Dish("french fries", true, 530, Dish.Type.OTHER));//

		// 필터링을 걸기
		System.out.println("5.2 스트림 슬라이싱");
		System.out.println("5.2.1 프레디케이트를 이용한 슬라이싱");
		System.out.println("Filtered sorted menu: dish.getCalories() < 320");
		List<Dish> filteredMenu = specialMenu.stream().filter(dish -> dish.getCalories() < 320).collect(toList());
		filteredMenu.forEach(System.out::println);
		System.out.println();

		// while문으로 하되, 320 이하의 것들만 while(dist.getCalories < 320)
		System.out.println("TAKEWHILE 활용");
		System.out.println("Sorted menu sliced with takeWhile():");
		List<Dish> slicedMenu1 = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(toList());
		slicedMenu1.forEach(System.out::println);
		System.out.println();

		System.out.println("DROPWHILE 활용 : takewhile 이후의 것들만 리턴");
		System.out.println("Sorted menu sliced with dropWhile():");
		List<Dish> slicedMenu2 = specialMenu.stream().dropWhile(dish -> dish.getCalories() < 320).collect(toList());
		slicedMenu2.forEach(System.out::println);
		System.out.println();

		// 스트림 limit
		System.out.println("5.2.2 스트림 축소");
		List<Dish> dishesLimit3 = menu.stream().filter(d -> d.getCalories() > 300).limit(3).collect(toList());
		System.out.println("Truncating a stream:");
		dishesLimit3.forEach(System.out::println);
		System.out.println();

		// 요소 생략
		System.out.println("5.2.3 요소 건너뛰기");
		System.out.println("처음 n개 요소를 건너뛴다");
		List<Dish> dishesSkip2 = menu.stream().filter(d -> d.getCalories() > 300).skip(2).collect(toList());
		System.out.println("Skipping elements:");
		dishesSkip2.forEach(System.out::println);
		System.out.println();
	}

}
