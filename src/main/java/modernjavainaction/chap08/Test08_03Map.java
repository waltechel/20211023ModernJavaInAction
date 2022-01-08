package modernjavainaction.chap08;

import java.util.Map;
import java.util.Map.Entry;

public class Test08_03Map {

	public static void main(String[] args) {
		System.out.println("------ Working with Maps ------");

		System.out.println("--> Iterating a map with a for loop");
		Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
		iterateMapwithForLoop(ageOfFriends);

		System.out.println("--> Iterating a map with forEach()");
		ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));
	}

	private static void iterateMapwithForLoop(Map<String, Integer> ageOfFriends) {
		for (Entry<String, Integer> entry : ageOfFriends.entrySet()) {
			String friend = entry.getKey();
			Integer age = entry.getValue();
			System.out.println(friend + " is " + age + " years old");
		}
	}
}
