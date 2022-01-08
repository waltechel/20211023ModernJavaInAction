package modernjavainaction.chap09;

/**
 * 전략 패턴은 한 유형의 알고리즘을 보유한 상태에서 런타임에 적절한 알고리즘을 선택하는 기법이다.
 * @author leedongjun
 *
 */
public class Test09_02_람다로_객체지향_디자인_패턴_리팩토링하기 {

	public static void main(String[] args) {
		// old school
		Validator v1 = new Validator(new IsNumeric());
		System.out.println(v1.validate("aaaa"));
		Validator v2 = new Validator(new IsAllLowerCase());
		System.out.println(v2.validate("bbbb"));

		// with lambdas
		Validator v3 = new Validator((String s) -> s.matches("\\d+"));
		System.out.println(v3.validate("aaaa"));
		Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
		System.out.println(v4.validate("bbbb"));
	}

	interface ValidationStrategy {
		boolean execute(String s);
	}

	static private class IsAllLowerCase implements ValidationStrategy {

		@Override
		public boolean execute(String s) {
			return s.matches("[a-z]+");
		}

	}

	static private class IsNumeric implements ValidationStrategy {

		@Override
		public boolean execute(String s) {
			return s.matches("\\d+");
		}

	}

	static private class Validator {

		private final ValidationStrategy strategy;

		public Validator(ValidationStrategy v) {
			strategy = v;
		}

		public boolean validate(String s) {
			return strategy.execute(s);
		}

	}

}
