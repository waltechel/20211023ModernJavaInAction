package modernjavainaction.chap06;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static modernjavainaction.chap06.Dish.dishTags;
import static modernjavainaction.chap06.Dish.menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Test06_03Grouping {

	enum CaloricLevel {
		DIET, NORMAL, FAT
	};

	public static void main(String... args) {
		System.out.println("Dishes grouped by type: " + groupDishesByType() + "\n");
		System.out.println("Dish names grouped by type: " + groupDishNamesByType() + "\n");
		System.out.println("Dish tags grouped by type: " + groupDishTagsByType() + "\n");
		System.out.println("Caloric dishes grouped by type(BAD): " + groupCaloricDishesByTypeBad() + "\n");
		System.out.println("Caloric dishes grouped by type(GOOD): " + groupCaloricDishesByTypeGood() + "\n");
		System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel() + "\n");
		System.out.println("여기서부터 다수준 그룹화");
		System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel() + "\n");
		System.out.println("Count dishes in groups: " + countDishesInGroups() + "\n");
		System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType() + "\n");
		System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals() + "\n");
		System.out.println("Sum calories by type: " + sumCaloriesByType() + "\n");
		System.out.println("Caloric levels by type: " + caloricLevelsByType() + "\n");
	}

	

	/**
	 * 팩토리 메서드 Collectors.groupingBy를 이용해서 쉽게 메뉴를 그룹화할 수 있다.
	 * @return
	 */
	private static Map<Dish.Type, List<Dish>> groupDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType));
	}

	/**
	 * 그룹의 각 요리를 관련 이름 목록으로 변환할 수 있다.
	 * @return
	 */
	private static Map<Dish.Type, List<String>> groupDishNamesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
	}

	/**
	 * 각 형식의 요리의 태그를 flatMapping 컬렉터를 이용해서 간편하게 추출할 수 있다.
	 * @return
	 */
	private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
		return menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));
	}

	/** 
	 * 미리 조건에서 제거해버려서 fish 가 나오지 않는 문제가 발생한다.
	 * @return
	 */
	private static Map<Dish.Type, List<Dish>> groupCaloricDishesByTypeBad() {
		return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
	}
	
	/** 
	 * fish가 비록 값이 없다고 하더라도 조회가 된다.
	 * @return
	 */
	private static Map<Dish.Type, List<Dish>> groupCaloricDishesByTypeGood() {
		return menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
	}

	/**
	 * 람다 표현식으로 필요한 로직을 구현할 수 있다.
	 * @return
	 */
	private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
		return menu.stream().collect(groupingBy(dish -> {
			if (dish.getCalories() <= 400) {
				return CaloricLevel.DIET;
			} else if (dish.getCalories() <= 700) {
				return CaloricLevel.NORMAL;
			} else {
				return CaloricLevel.FAT;
			}
		}));
	}

	/**
	 * 다수준 그룹화, 메뉴 타입과 칼로리 레벨로 구분하였다.
	 * @return
	 */
	private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
		return menu.stream().collect(groupingBy(Dish::getType, groupingBy((Dish dish) -> {
			if (dish.getCalories() <= 400) {
				return CaloricLevel.DIET;
			} else if (dish.getCalories() <= 700) {
				return CaloricLevel.NORMAL;
			} else {
				return CaloricLevel.FAT;
			}
		})));
	}

	/**
	 * 메뉴에서 요리의 수를 종류별로 계산할 수 있다.
	 * @return
	 */
	private static Map<Dish.Type, Long> countDishesInGroups() {
		return menu.stream().collect(groupingBy(Dish::getType, counting()));
	}

	/**
	 * 각 서브그룹에서 가장 칼로리가 높은 요리 찾기
	 * @return
	 */
	private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
	}
	
	/**
	 * 각 서브그룹에서 가장 칼로리가 높은 요리 찾기
	 * @return
	 */
	private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
		return menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2), Optional::get)));
	}

	/**
	 * 각 그룹으로 분류된 요리에 이 컬렉터를 활용한다.
	 * @return
	 */
	private static Map<Dish.Type, Integer> sumCaloriesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
	}

	/**
	 * groupingBy와 mapping 컬렉터를 합쳐서 기능을 구현할 수 있다.
	 * @return
	 */
	private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
			if (dish.getCalories() <= 400) {
				return CaloricLevel.DIET;
			} else if (dish.getCalories() <= 700) {
				return CaloricLevel.NORMAL;
			} else {
				return CaloricLevel.FAT;
			}
		}, toSet())));
	}

}
