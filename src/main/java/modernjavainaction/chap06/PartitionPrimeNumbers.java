package modernjavainaction.chap06;

import static java.util.stream.Collectors.partitioningBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PartitionPrimeNumbers {

	public static void main(String... args) {
		System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
		System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));
	}

	/**
	 * n까지의 자연수를 소수와 비소수로 분할 할 수 있다.
	 * 
	 * @param n
	 * @return
	 */
	public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
		return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
	}

	public static boolean isPrime(int candidate) {
		return IntStream.rangeClosed(2, candidate - 1).limit((long) Math.floor(Math.sqrt(candidate)) - 1).noneMatch(i -> candidate % i == 0);
	}

	public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
		return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
	}

	private static boolean isPrime(List<Integer> primes, Integer candidate) {
		double candidateRoot = Math.sqrt(candidate);
		return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
	}

	public static boolean isPrimeTakeWhile(List<Integer> primes, Integer candidate) {
		return takeWhile(primes, i -> i <= (int) Math.sqrt(candidate)).stream().noneMatch(i -> candidate % i == 0);
	}

	public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
		int i = 0;
		for (A item : list) {
			if (!p.test(item)) {
				return list.subList(0, i);
			}
			i++;
		}
		return list;
	}

	public Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
		return Stream.iterate(2, i -> i + 1).limit(n).collect(() -> new HashMap<Boolean, List<Integer>>() {
			{
				put(true, new ArrayList<Integer>());
				put(false, new ArrayList<Integer>());
			}
		}, (acc, candidate) -> {
			acc.get(isPrime(acc.get(true), candidate)).add(candidate);
		}, (map1, map2) -> {
			map1.get(true).addAll(map2.get(true));
			map1.get(false).addAll(map2.get(false));
		});
	}

}
