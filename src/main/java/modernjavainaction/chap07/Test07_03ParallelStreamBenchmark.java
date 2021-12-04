package modernjavainaction.chap07;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = { "-Xms8G", "-Xmx8G" })
@Measurement(iterations = 2)
@Warmup(iterations = 3)
public class Test07_03ParallelStreamBenchmark {

	private static final long N = 100_000_000L;

	
	// Result
	// "modernjavainaction.chap07.ParallelStreamBenchmark.iterativeSum":
	// 4.140 ±(99.9%) 0.807 ms/op [Average]
	// (min, avg, max) = (3.996, 4.140, 4.280), stdev = 0.125
	// CI (99.9%): [3.334, 4.947] (assumes normal distribution)
	@Benchmark
	public long iterativeSum() {
		long result = 0;
		for (long i = 1L; i <= N; i++) {
			result += i;
		}
		return result;
	}

	// Result
	// "modernjavainaction.chap07.ParallelStreamBenchmark.sequentialSum":
	// 90.315 ±(99.9%) 20.305 ms/op [Average]
	// (min, avg, max) = (87.512, 90.315, 94.290), stdev = 3.142
	// CI (99.9%): [70.010, 110.619] (assumes normal distribution)
	@Benchmark
	public long sequentialSum() {
		return Stream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
	}

	// Result
	// "modernjavainaction.chap07.ParallelStreamBenchmark.parallelSum":
	// 125.966 ±(99.9%) 32.371 ms/op [Average]
	// (min, avg, max) = (121.362, 125.966, 130.506), stdev = 5.010
	// CI (99.9%): [93.595, 158.338] (assumes normal distribution)
	@Benchmark
	public long parallelSum() {
		return Stream.iterate(1L, i -> i + 1).limit(N).parallel().reduce(0L, Long::sum);
	}

	// Result
	// "modernjavainaction.chap07.ParallelStreamBenchmark.rangedSum":
	// 5.533 ±(99.9%) 0.881 ms/op [Average]
	// (min, avg, max) = (5.456, 5.533, 5.737), stdev = 0.136
	// CI (99.9%): [4.652, 6.414] (assumes normal distribution)
	@Benchmark
	public long rangedSum() {
		return LongStream.rangeClosed(1, N).reduce(0L, Long::sum);
	}

	// Result
	// "modernjavainaction.chap07.ParallelStreamBenchmark.parallelRangedSum":
	// 19.677 ±(99.9%) 38.463 ms/op [Average]
	// (min, avg, max) = (14.586, 19.677, 26.526), stdev = 5.952
	// CI (99.9%): [? 0, 58.139] (assumes normal distribution)
	@Benchmark
	public long parallelRangedSum() {
		return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
	}

	//	Benchmark                                  Mode  Cnt    Score    Error  Units
	//	ParallelStreamBenchmark.iterativeSum       avgt    4    4.140 ±  0.807  ms/op
	//	ParallelStreamBenchmark.parallelRangedSum  avgt    4   15.621 ± 22.836  ms/op
	//	ParallelStreamBenchmark.parallelSum        avgt    4  109.998 ± 13.458  ms/op
	//	ParallelStreamBenchmark.rangedSum          avgt    4    5.144 ±  1.056  ms/op
	//	ParallelStreamBenchmark.sequentialSum      avgt    4   90.315 ± 20.305  ms/op
	@TearDown(Level.Invocation)
	public void tearDown() {
		System.gc();
	}

}
