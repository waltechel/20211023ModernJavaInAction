package modernjavainaction.chap08;

import java.util.Arrays;
import java.util.List;

public class Test08_01CollectionFactory {

	public static void main(String[] args) {

		List<String> friends2 = Arrays.asList("Raphael", "Olivia");
		friends2.set(0, "Richard");
		friends2.add("Thibaut");
		// Exception in thread "main" java.lang.UnsupportedOperationException
		// 이게 왜 좋은지는 모르겠다 되어야 하는 게 아닌가?
	}
}
