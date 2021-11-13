package modernjavainaction.chap05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

/**
 * 5.3 매핑
 * @author leedongjun
 *
 */
class Test05_03 {

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

		// map
		System.out.println("5.3.1 스트림의 각 요소에 함수 적용하기");
		System.out.println("다음 소스는 스트림의 요리명을 추출하는 함수이다.");
		List<String> dishNames = menu.stream().map(Dish::getName).collect(toList());
		System.out.println(dishNames);
		System.out.println();

		// map
		System.out.println("다음 소스는 단어의 길이를 추출하는 함수이다.");
		List<String> words = Arrays.asList("Hello", "World", "this", "is", "modern", "java", "in", "Action");
		List<Integer> wordLengths = words.stream().map(String::length).collect(toList());
		System.out.println(wordLengths);
		System.out.println();

		System.out.println("5.3.2 스트림 평면화");
		System.out.println("고유 문자로 이루어진 리스트를 반환한다.");
		System.out.println("flatMap을 이용해서 생성된 스트림들을 하나의 스트림으로 평탄화");
		// flatMap
		words.stream() //
		.flatMap((String line) -> Arrays.stream(line.split(""))) // flatMap을 이용해서 스트림들을 하나의 문자 스트림으로 변환  
				.distinct()
				.forEach(System.out::print);
		System.out.println();

		// flatMap
		System.out.println();
		List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> numbers2 = Arrays.asList(6, 7, 8);
		List<int[]> pairs = numbers1.stream()//
				.flatMap((Integer i) -> numbers2.stream().map((Integer j) -> new int[] { i, j }))//
				.filter(pair -> (pair[0] + pair[1]) % 3 == 0)//
				.collect(toList());
		pairs.forEach(pair -> System.out.printf("(%d, %d) ,", pair[0], pair[1]));
	}

}
