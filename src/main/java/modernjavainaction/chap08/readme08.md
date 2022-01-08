# chatper 08 컬렉션 API 개선

## 8.1 컬렉션 팩토리

### UnsupportedOperationException 예외 발생

### 8.1.1 리스트 팩토리

## 8.2 리스트와 집합 처리
- 결과적으로 반복자의 상태가 컬렉션의 상태와 서로 동기화되지 않는다. 

## 8.3 맵 처리

## 8.4 개선된 ConcurrentHashMap

## 8.5 마치며
- 자바 9는 List.of, Set.of, Map.of, Map.ofEntries 등의 컬렉션 팩토리를 지원한다
- 이들 컬렉션 팩토리가 반환한 객체는 만들어진 다음 바꿀 수 없다.