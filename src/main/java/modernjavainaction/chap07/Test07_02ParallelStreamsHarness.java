package modernjavainaction.chap07;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class Test07_02ParallelStreamsHarness {

	public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

	public static void main(String[] args) {
		
		// Result: 5000000050000000
		// Iterative Sum done in: 41 msecs
		// Sequential Sum done in: 1328 msecs
		// Parallel forkJoinSum done in: 6857 msecs
		// Range forkJoinSum done in: 203 msecs
		// Parallel range forkJoinSum done in: 83 msecs
		// ForkJoin sum done in: 358 msecs
		// SideEffect sum done in: 544 msecs
		// SideEffect parallel sum done in: 664 msecs
		// Result: 2713697880666365
		
		System.out.println("Iterative Sum done in: " + measurePerf(Test07_01ParallelStreams::iterativeSum, 100_000_000L) + " msecs");
		System.out.println("Sequential Sum done in: " + measurePerf(Test07_01ParallelStreams::sequentialSum, 100_000_000L) + " msecs");
		System.out.println("Parallel forkJoinSum done in: " + measurePerf(Test07_01ParallelStreams::parallelSum, 100_000_000L) + " msecs");
		System.out.println("Range forkJoinSum done in: " + measurePerf(Test07_01ParallelStreams::rangedSum, 100_000_000L) + " msecs");
		System.out.println("Parallel range forkJoinSum done in: " + measurePerf(Test07_01ParallelStreams::parallelRangedSum, 100_000_000L) + " msecs");
		System.out.println("ForkJoin sum done in: " + measurePerf(Test07_04ForkJoinSumCalculator::forkJoinSum, 100_000_000L) + " msecs");
		System.out.println("SideEffect sum done in: " + measurePerf(Test07_01ParallelStreams::sideEffectSum, 100_000_000L) + " msecs");
//		맨 마지막 이거는 정말 엉망임. 값이 다르게 나옴
		System.out.println("SideEffect parallel sum done in: " + measurePerf(Test07_01ParallelStreams::sideEffectParallelSum, 100_000_000L) + " msecs");
	}

	public static <T, R> long measurePerf(Function<T, R> f, T input) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			R result = f.apply(input);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + result);
			if (duration < fastest) {
				fastest = duration;
			}
		}
		return fastest;
	}

}
