# chapter04 스트림 소개

## 4.1 스트림이란 무엇인가?

- 스트림을 사용하면 데이터를 분명하게 병렬로 처리할 수 있다

```java
// 스트림을 사용하면 데이터를 분명하게 병렬로 처리할 수 있다
List<String> lowCaloricDishesName = menu.stream()
										.filter(d -> d.getCalories() < 400)
										.sorted(comparing(Dish::getCalories))
										.map(Dish::getName)
										.collect(toList());

// 스트림을 사용하면 데이터를 분명하게 병렬로 처리할 수 있다
List<String> lowCaloricDishesName = menu.parallelStream()
										.filter(d -> d.getCalories() < 400)
										.sorted(comparing(Dish::getCalories))
										.map(Dish::getName)
										.collect(toList());

```

- 자바 8의 스트림 API의 특징을 다음처럼 요약할 수 있다
- 선언형 : 더 간결하고 가독성이 좋아진다.
- 조립할 수 있다 : 유연성이 좋아진다
- 병렬화 : 성능이 좋아진다.

## 4.2 스트림 시작하기

- 스트림 : 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소 
	- 연속된 요소 : 벡터값을 나타내지만, 컬렉션과 비교해서 스트림은 데이터가 아닌 계산에 초점을 맞춘다.
	- 소스 : 데이터 제공 소스로부터 데이터를 소비한다
	- 스트림 연산은 데이터 처리에서 지원하는 듯한 연산과 비슷한 연산을 지원한다.
	- 파이프라이닝 : 대부분의 스트림 연산은 스트림 자기 자신을 반환한다
	- 내부 반복 : 명시적으로 반복하는 컬렉션과 달리 스트림은 내부 반복을 지원한다.

## 4.3 스트림과 컬렉션

- 컬렉션 
	- 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조
- 스트림
	- 스트림에 요소를 추가하거나 제거할 수 없다
	- 사용자가 요청하는 ㄱ값만 스트림에서 추출한다

### 4.3.1 딱 한 번만 탐색할 수 있다

- 탐색된 스트림의 요소는 소비되어, 한번 탐색한 요소를 다시 탐색하려면 초기 데이터 소스에서 새로운 스트림을 만들어야 한다

### 4.3.2 외부 반복과 내부 반복

- 컬렉션 인터페이스를 반복하려면 개발자가 직접 요소를 반복하는데, 이를 외부 반복(External iteration) 이라 한다.
- 스트림 라이브러리는 반복을 알아서 처리하고 결과 스트림을 알아서 저장하는데, 이를 내부 반복(Internal iteration) 이라 한다.

```java
List<String> menuNames = menu.stream()
						.map(Dish::getName)
						.collect(toList());
```

## 4.4 스트림 연산

- 연결할 수 있는 연산을 중간 연산, 스트림을 닫는 연산을 최종 연산이라고 한다. 

### 4.4.1 중간 연산

- 중간 연산의 중요한 특징은 단말 연산을 실행하기 전까지는 아무 연산도 수행하지 않는다.
	- Stream<T> filter(Predicate<T> param), 
	- Stream<R> map(Function<T, R> param), 
	- Stream<T> limit, 
	- Stream<T> sorted(Comparator<T> param), 
	- Stream<T> distinct

### 4.4.2 최종 연산

- 최종 연산은 스트림 파이프라인에서 결과를 도출한다.
	- void forEach(),
	- long count(),
	- collect()

### 4.4.3 스트림 이용하기

- 스트림의 과정
1. 데이터 소스
2. 스트림 파이프라인을 구성할 중간 연산 연결
3. 스트림 파이프라인을 실행하고 결과를 만들 최종 연산 

## 4.5 로드맵

## 4.6 마치며

- 스트림은 소스에서 추출된 연속 요소로, 데이터 처리 연산을 지원한다.
- 스트림은 내부 반복을 지원한다. 
- 중간 연산과 최종 연산
- 스트림 요청은 느리게 처리된다(lazy)