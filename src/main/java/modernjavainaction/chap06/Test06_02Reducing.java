package modernjavainaction.chap06;

import static java.util.stream.Collectors.reducing;
import static modernjavainaction.chap06.Dish.menu;

public class Test06_02Reducing {

	public static void main(String... args) {
		System.out.println("Total calories in menu: " + calculateTotalCalories());
		System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
		System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
		System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
	}

	/**
	 * reducing 메서드로 만들어진 컬렉터로도 메뉴의 모든 칼로리 합계를 계산할 수 있다.
	 * @return
	 */
	private static int calculateTotalCalories() {
		return menu.stream().collect(reducing(0, Dish::getCalories, (Integer i, Integer j) -> i + j));
	}

	/**
	 * 다음처럼 한 개의 인수를 가진 reducing 버전을 이용해서 모든 칼로리 합계를 계산할 수 있다.
	 * @return
	 */
	private static int calculateTotalCaloriesWithMethodReference() {
		return menu.stream().collect(reducing(0, // 초깃값 
						Dish::getCalories, // 합계 함수
						Integer::sum));	// 변환 함수
	}

	/**
	 * 요리의 칼로리로 매핑한 다음에 이전 버전의 예제에서 사용한 메서드 참조로 결과 스트림을 리듀싱한다. 
	 * @return
	 */
	private static int calculateTotalCaloriesWithoutCollectors() {
		return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
	}

	/**
	 * 스트림을 IntStream으로 매핑한 다음에 sum 메서드를 호출하는 방법으로도 결과를 얻을 수 있다.
	 * @return
	 */
	private static int calculateTotalCaloriesUsingSum() {
		return menu.stream().mapToInt(Dish::getCalories).sum();
	}

}
