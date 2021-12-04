package modernjavainaction.chap07;

import static modernjavainaction.chap07.Test07_02ParallelStreamsHarness.FORK_JOIN_POOL;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class Test07_04ForkJoinSumCalculator extends RecursiveTask<Long> {

	// 이 값이하의 서브태스크는 더이상 분할할 수 없다.
	public static final long THRESHOLD = 10_000;

	private final long[] numbers;
	private final int start;
	private final int end;

	// 메인 태스크를 생성할 때 사용할 공개 생성자
	public Test07_04ForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}

	// 비공개 생성자
	private Test07_04ForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		int length = end - start;
		// THRESHOLD보다 작으면 computeSequentially하게 계산한다.
		if (length <= THRESHOLD) {
			return computeSequentially();
		}
		// 첫 번째 절반을 더하도록 서브태스크를 생성한다.
		Test07_04ForkJoinSumCalculator leftTask = new Test07_04ForkJoinSumCalculator(numbers, start, start + length / 2);
		// 비동기 실행
		leftTask.fork();
		// 나머지 절반을 더하도록 한다.
		Test07_04ForkJoinSumCalculator rightTask = new Test07_04ForkJoinSumCalculator(numbers, start + length / 2, end);
		// 동기 실행
		Long rightResult = rightTask.compute();
		// leftTask 값을 다시 읽는다.
		Long leftResult = leftTask.join();
		return leftResult + rightResult;
	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}

	public static long forkJoinSum(long n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new Test07_04ForkJoinSumCalculator(numbers);
		return FORK_JOIN_POOL.invoke(task);
	}

}
