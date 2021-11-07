# chapter 03 람다 표현식

## 3.1 람다란 무엇인가?

- 람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것

- 람다의 특징
	- 익명
	- 함수
	- 전달
	- 간결성

```java
Comparator<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());

```

## 3.2 어디에, 어떻게 람다를 사용할까?
### 3.2.1. 함수형 인터페이스
- 함수형 인터페이스를 인수로 받는 메서드에만 람다 표현식을 사용할 수 있다.
- 함수형 인터페이스는 정확히 하나의 추상 메서드를 지정하는 인터페이스이다.
- 많은 디폴트 메서드가 있더라도 추상 메서드가 오직 하나라면 함수형 인터페이스이다. 

```java
Runnable r1 = () -> sysout("hello world!");

Runnable r2 = new Runnable(){
	@Override
	public void run(){
		sysout("hello world!");
	}
}

public static void process(Runnable r){
	r.run();
}

process(r1);
process(r2);
process(() -> sysout("hello world! this is different because it is lambda expression"));

```

### 3.2.2. 함수 디스크립터
- @FunctionalInterface 어노테이션은 함수형 인터페이스를 가리키는 어노테이션이다.
- 만약 함수형 인터페이스가 아니라면 컴파일 타임에서 에러가 난다. 

## 3.3 람다 활용 : 실행 어라운드 패턴
- 실제 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싸는 형태를 갖는데, 이런 코드를 실행 어라운드 패턴이라고 한다.

```java
public String processFile() throws IOException{
	try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
		return br.readLine();
	}
}

```
### 3.3.1 1단계 : 동작 파라미터화를 기억하라
### 3.3.2 2단계 : 함수형 인터페이스를 이용해서 동작 전달
### 3.3.3 3단계 : 동작 실행
### 3.3.4 4단계 : 람다 전달

```java
public Interface BufferedReaderProcessor{
	String process(BufferedReader b) throws IOException;
}

public String processFile(BufferedReaderProcessor brp){
	try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
		return brp.process(br);
	}
}

String oneLine = processLine((BufferedReader br) -> br.readLine());

String twoLines = processLine((BufferedReader br) -> br.readLine() + br.readLine());

```
## 3.4 함수형 인터페이스 사용

### 3.4.1 Predicate
- java.util.function.Predicate<T> 인터페이스는 test 라는 추상 메서드를 정의하며 test는 제네릭 형식 T의 객체를 인수로 받아 불리언을 반환한다.(input T, output boolean)

```java
@FunctionalInterface
public Interface Predicate<T>{
	boolean test(T t);
}

public <T> List<T> filter(List<T> list, Predicate<T> p){
	List<T> results = new ArrayList<>();
	for(T t: list){
		if(p.test(t)){
			results.add(t);
		}
	}
	return results;
}

Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
// listOfStrings : ["hello", "dongjun", ""]
// nonEmpty : ["hello", "dongjun"]


```

### 3.4.2 Consumer
- java.util.function.Consumer<T> 인터페이스는 제네릭 형식 T 객체를 받아서 void를 반환하는 accept 라는 추상 메서드를 정의한다.(input T, output void)
- T 형식의 객체를 인수로 받아서 그저 동작만을 수행하고 싶을 때 Consumer 인터페이스를 사용하면 된다. 

```java
@FunctionalInterface
public Interface Consumer<T>{
	void accept(T t);
}

public <T> List<T> forEach(List<T> list, Consumer<T> c){
	for(T t : list){
		c.accept(t);
	}
}

forEach(
	Arrays.asList(1,2,3,4,5), (Integer i) -> sysout(i)
);
// 1
// 2
// 3
// 4
// 5

```

### 3.4.3 Function
- java.util.function.Function<T, R> 인터페이스는 제네릭 형식 T 객체를 받아서 제네릭 형식 R을 반환하는 apply 라는 추상 메서드를 정의한다.(input T, output R)
- T 형식의 객체를 인수로 받아서 리턴 값을 받고 싶을 때 Function 인터페이스를 사용한다.  
```java
@FunctionalInterface
public Interface Function<T, R>{
	R apply(T t);
}

public <T, R> List<R> map(List<T> list, Function<T, R> f){
	List<R> result = new ArrayList<>();
	for(T t : list){
		result.add(f.apply(t));
	}
	return result;
}

List<Integer> lengths = map(
	Arrays.asList("lambda", "hello", "dongjun"), (String s) -> s.length()
)
// [6, 5, 7]

```

#### 기본형 고정하는 함수형 인터페이스
- java8 에서는 기본형을 입출력으로 사용하는 상황에서 오토박싱 동작을 피할 수 있도록 특별한 버전의 함수형 인터페이스를 사용한다.
```java
public Interface IntPredicate{
	boolean test(int t);
}

// 박싱이 일어나지 않는다.
IntPredicate evenNumbers = (int i) -> i % 2 == 0;
evenNumbers.test(1000);

// 박싱이 일어난다.
Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
evenNumbers.test(1001);

```
#### 예외, 람다, 함수형 인터페이스의 관계
- 람다라도 예외처리를 할 수 있다.
```java
Functional<BufferedReader, String> f = (BufferedReader b) -> {
	try{
		return b.readLine();
	}catch{
		throw new RuntimeException(e);
	}
}
```

## 3.5 형식 검사, 형식 추론, 제약

### 3.5.1 형식 검사

```java
List<Apple> heavierThan150g = filter(inventory, (Apple apple) -> apple.getWeight() > 150);
```
1. filter 메서드의 선언을 확인한다.
2. filter 메서드는 두 번째 파라미터로 Predicate<Apple> 형식을 기대한다.
3. Predicate<Apple>은 테스트라는 한 개의 추상 메서드를 정의하는 함수형 인터페이스이다.
4. test 메서드는 Apple을 받아 boolean을 반환하는 함수이다.
5. filter 메서드로 전달된 인수는 위와 같은 요구사항을 만족하여야 한다. 

### 3.5.2 같은 람다, 다른 함수형 인터페이스

#### 다이아몬드 연산자

#### 특별한 void 호환 규칙
- 람다의 바디에 일반 표현식이 있으면 void를 반환하는 함수 디스크립터와 호황된다. 

### 3.5.3 형식 추론

### 3.5.4 지역 변수 사용
- 람다에서 참고하는 지역 변수는 final로 선언되거나 실질적으로 final 처럼 취급되어야 한다. 
```java
int portNumber = 8080;
// 여기는 가능
Runnable r = () -> sysout(portNumber);

```
- lambda식에 사용된 지역변수를 final처럼 사용해야 하는 이유
- lambda식에 사용된 지역변수는 함수 콜스택에서 사용되는 지역변수인데,     
함수가 끝난 다음 삭제되더라도 변하면 안되기 때문에 지역변수를 수정하는 데 제한이 있다.
- lambda식에 사용된 지역변수를 왜 final로만 사용해야 하는지를 찾아보고 공유해보기

- 블로그에서 퍼온 글
- https://frhyme.github.io/java/java_variable_used_in_lambda_expressiond/
- effectively final: final이 선언되지는 않았지만, 값이 바뀌지 않은 경우,     
즉 컴파일러 단에서 해당 변수의 값이 한번도 바뀌지 않았다면 “애는 충분히 final이야”라고 분류해주는 것을 보이네요.

- 블로그에서 퍼온 글 2
- https://www.baeldung.com/java-lambda-effectively-final-local-variables
- The basic reason this won't compile is that the lambda is capturing the value of start, meaning making a copy of it. 
Forcing the variable to be final avoids giving the impression that incrementing start inside the lambda could actually modify the start method parameter.

```java
int portNumber = 8080;
// 여기는 가능
Runnable r = () -> sysout(portNumber);
// 여기는 불가능
portNumber = 8090;

```
#### 지역 변수의 제약
#### 클로저
- 람다에서 정의된 메서드의 지역 변숫값은 final 변수여야 한다. 

## 3.6 메서드 참조
### 3.6.1 요약
```java
// 이렇게 씀으로써 가독성을 높일 수 있다.
inventory.sort((Apple a1, Apple a2) -> Integer.compare(a1.getWeight(), a2.getWeight()));
inventory.sort(comparing(Apple::getWeight));
```

#### 메서드 참조를 만드는 방법
1. 정적 메서드 참조
	- Integer::parseInt
2. 다양한 형식의 인스턴스 메서드 참조
	- String::length
3. 기존 객체의 인스턴스 메서드 참조

### 3.6.2 생성자 참조
- ClassName::new 스타일로 기존 생성자의 참조를 만들 수 있다. 
```java
Supplier<Apple> c1 = Apple::new;
Apple a1 = c1.get();
Apple a2 = c1.get();
sysout(a1 == a2)//false
```
## 3.7 람다, 메서드 참조 활용하기

### 3.7.1 1단계 : 코드 전달

### 3.7.2 2단계 : 익명 클래스 사용

### 3.7.3 3단계 : 람다 표현식 사용
```java
import static java.util.Comparator.comparing;
inventory.sort(comparing(apple -> apple.getWeight()));
```
### 3.7.4 4단계 : 메서드 참조 사용
```java
inventory.sort(comparing(Apple::getWeight));
```

## 3.8 람다 표현식을 조합할 수 있는 유용한 메서드
- 람다 표현식은 조합이 가능하다는 것이 큰 장점이다.

### 3.8.1. Comparator 조합
```java
Comparator<Apple> c = Comparator.comparing(Apple::getWeight);

```
#### 역정렬
```java
inventory.sort(comparing(Apple::getWeight).reversed());
```
#### Comparator 연결
```java
inventory.sort(comparing(Apple::getWeight)
			.reversed()
			.thenComparing(Apple::getCountry));
```

### 3.8.2 Predicate 조합
- negate(반전), and, or 연산을 쓸 수있다.
```java
Predicate<Apple> notRedApple = redApple.negate();

// 확인 필요 
Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(apple -> apple.getWeight() > 150)
													.or(apple -> GREEN.equals(a.getColor()));
```

### 3.8.3 Function 조합
- 합성함수
```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = x -> f.andThen(g);
sysout(h(1)) // 4

```
- 합성함수
```java
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = x -> f.compose(g);
sysout(h(1)) // 3

```
## 3.9 람다와 비슷한 수학적 개념

### 3.9.1 적분

### 3.9.2 자바 8 람다로 연결

## 3.10 마치며

- 람다 표현식은 익명 함수의 일종이다.
- 함수형 인터페이스는 하나의 추상 메서드만을 정의하는 인터페이스다.


