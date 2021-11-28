package modernjavainaction.chap06;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static modernjavainaction.chap06.Dish.menu;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;

public class Test06_02Summarizing {

	public static void main(String... args) {
		// 컬렉터로 메뉴에서 요리 수를 계산한다
		System.out.println("Number of dishes 1: " + howManyDishes1());
		System.out.println("Number of dishes 2: " + howManyDishes2());
		System.out.println("The most caloric dish is: " + findMostCaloricDish());
		System.out.println("The most caloric dish is: " + findMostCaloricDishUsingComparator());
		System.out.println("Total calories in menu: " + calculateTotalCalories());
		System.out.println("Average calories in menu: " + calculateAverageCalories());
		System.out.println("Menu statistics: " + calculateMenuStatistics());
		System.out.println("Short menu: " + getShortMenu());
		System.out.println("Short menu comma separated: " + getShortMenuCommaSeparated());
	}

	/**
	 * 메뉴의 종류를 계산한다.
	 * @return
	 */
	private static long howManyDishes2() {
		return menu.stream().count();
	}

	/**
	 * 메뉴의 종류를 계산한다.
	 * @return
	 */
	private static long howManyDishes1() {
		return menu.stream().collect(counting());
	}

	/**
	 * 가장 비싼 칼로리를 가진 음식을 보여준다
	 * @return
	 */
	private static Dish findMostCaloricDish() {
		return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
	}

	/**
	 * 가장 비싼 칼로리를 가진 음식을 보여준다
	 * @return
	 */
	private static Dish findMostCaloricDishUsingComparator() {
		Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
		BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
		return menu.stream().collect(reducing(moreCaloricOf)).get();
	}

	/**
	 * 메뉴 리스트의 총 칼로리를 계산하는 코드
	 * @return
	 */
	private static int calculateTotalCalories() {
		return menu.stream().collect(summingInt(Dish::getCalories));
	}

	/**
	 * 메뉴 리스트의 평균 칼로리를 계산하는 코드
	 * @return
	 */
	private static Double calculateAverageCalories() {
		return menu.stream().collect(averagingInt(Dish::getCalories));
	}

	/**
	 * 메뉴 리스트의 카운트, sum, min, average, max 등등을 출력하는 코드
	 * @return
	 */
	private static IntSummaryStatistics calculateMenuStatistics() {
		return menu.stream().collect(summarizingInt(Dish::getCalories));
	}

	/**
	 * 메뉴의 모든 요리명을 연결하는 코드
	 * @return
	 */
	private static String getShortMenu() {
		return menu.stream().map(Dish::getName).collect(joining());
	}

	/**
	 * 메뉴의 모든 요리명을 컴마로 연결하는 코드
	 * @return
	 */
	private static String getShortMenuCommaSeparated() {
		return menu.stream().map(Dish::getName).collect(joining(", "));
	}

}
