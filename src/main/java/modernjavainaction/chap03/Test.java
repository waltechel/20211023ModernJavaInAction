package modernjavainaction.chap03;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Test {

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> results = new ArrayList<>();
		for (T t : list) {
			if (p.test(t)) {
				results.add(t);
			}
		}
		return results;
	}

	public static void main(String[] args) {

		Predicate<Apple> redApple = (Apple apple) -> apple.getColor().equals(Color.RED);
		// 들어가는 element 하나하나 돌아가는 것으로 생각하여야 한다.
		Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(apple -> apple.getWeight() > 150)
				.or(apple -> apple.getColor().equals(Color.GREEN));

		List<Apple> list = new ArrayList<>();
		list.add(new Apple(100, Color.RED));
		list.add(new Apple(200, Color.RED));
		list.add(new Apple(100, Color.GREEN));
		list.add(new Apple(200, Color.GREEN));
		List<Apple> listByCondition = filter(list, redAndHeavyAppleOrGreen);
		System.out.println(listByCondition);
	}

}
