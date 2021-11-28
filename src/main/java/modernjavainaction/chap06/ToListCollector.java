package modernjavainaction.chap06;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * supplier, accumulator, finisher, combiner, characteristics 다섯 개를 오버라이드하였다.
 * @author leedongjun
 *
 * @param <T>
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

	/**
	 * 새로운 결과 컨테이너 만들기
	 */
	@Override
	public Supplier<List<T>> supplier() {
		// 수집 연산의 시발점
		return () -> new ArrayList<T>();
	}

	/**
	 * 결과 컨테이너에 요소 추가하기
	 */
	@Override
	public BiConsumer<List<T>, T> accumulator() {
//		return List::add;
		// 탐색한 항목을 누적하고 바로 누적자를 고친다.
		return (list, item) -> list.add(item); 
	}

	/**
	 * 최종 변환값을 결과 컨테이너로 적용하기
	 */
	@Override
	public Function<List<T>, List<T>> finisher() {
		// 항등 함수
//		return i -> i;
		return Function.identity();
	}

	/**
	 * 두 결과 컨테이너 병합
	 */
	@Override
	public BinaryOperator<List<T>> combiner() {
		// 두 번째 콘텐츠와 합쳐져서 첫 번째 누적자를 고친다.
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	/**
	 * - UNORDERED : 리듀싱 결과는 스트림 요소의 방문 순서나 누적 순서에 영향을 받지 않는다.
	 * - CONCURRENT : 다중 스레드에서 accumulator 함수를 동시에 호출할 수있으며 이 컬렉터는 스트림의 병렬 리듀싱을 수행할 수 있따.
	 * - INDENTITY_FINISH : 따라서 리듀싱 과정의 최종 결과로 누적자 객체를 바로 사용할 수 있다.
	 */
	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
	}

}
