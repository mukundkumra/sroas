# OOP2 Assignment Coverage Review

This file maps the requested assignment features to the current codebase and records verification notes.

## Fundamentals

- **Sorting (`Comparator.comparing`)**: ✅ Covered in `MenuService.sortByPrice()`.
- **Lambdas (`Consumer`, `Predicate`, `Supplier`, `Function`)**: ✅ Covered in `MenuService.demonstrateLambdas()`.
- **Streams**:
  - **Terminal operations**:
    - `min()` ✅ (`AnalyticsService.runAnalytics`)
    - `max()` ✅ (`AnalyticsService.runAnalytics`)
    - `count()` ✅ (`AnalyticsService.runAnalytics`)
    - `findAny()` ✅ (`AnalyticsService.runAnalytics`)
    - `findFirst()` ✅ (`AnalyticsService.runAnalytics`)
    - `allMatch()` ✅ (`AnalyticsService.runAnalytics`)
    - `anyMatch()` ✅ (`AnalyticsService.runAnalytics`)
    - `noneMatch()` ✅ (`AnalyticsService.runAnalytics`)
    - `forEach()` ✅ (`MenuService.demonstrateLambdas`)
  - **`collect()` usage**:
    - `Collectors.toMap()` ✅ (`MenuService.demonstrateCollectors`)
    - `Collectors.groupingBy()` ✅ (`MenuService.demonstrateCollectors`)
    - `Collectors.partitioningBy()` ✅ (`MenuService.demonstrateCollectors`)
  - **Intermediate operations**:
    - `filter()` ✅ (`MenuService.demonstrateLambdas`)
    - `distinct()` ✅ (`MenuService.demonstrateIntermediateOps`)
    - `limit()` ✅ (`MenuService.demonstrateIntermediateOps`, `RestaurantApp.runInteractive`)
    - `map()` ✅ (`MenuService.demonstrateLambdas`, `MenuService.demonstrateIntermediateOps`)
    - `sorted()` ✅ (`MenuService.demonstrateIntermediateOps`)
- **Switch expressions and pattern matching**: ✅ Covered in `RestaurantApp.parsePayment` (switch expression) and `OrderService.paymentType` (pattern matching for switch).
- **Sealed classes and interfaces**: ✅ Covered by sealed `Payment` interface.
- **Date/Time API**: ✅ Covered by `LocalDateTime` in `Order`/`OrderService`; `DateUtil.jav` also demonstrates `DateTimeFormatter`, `ZoneId`, and `ZonedDateTime`.
- **Records**: ✅ Covered by `MenuItem` and `Order`.

## Advanced

- **Concurrency (`ExecutorService` + `Callable`)**: ✅ Covered in `KitchenService.processOrders`.
- **NIO2**: ✅ Covered by `Files`, `Path`, `Paths`, `StandardOpenOption` in `FileService`.
- **Localisation**: ✅ Covered in `LocaleManager` + `messages_en.properties` and `messages_es.properties`.
- **Java 25 - Compact source files + instance main methods (JEP 512)**: ✅ Covered via the instance `main()` method in `RestaurantApp`.
- **Java 25 - Flexible constructor bodies (JEP 513)**: ✅ Covered in `MenuService(String modeInput)` where logic appears before `this()`.

## Extra marks

- **Java 25 - Scoped Values**: ✅ Covered (`ScopedValue` in `RestaurantApp`).
- **Java 25 - Stream Gatherers**: ✅ Covered in `GathererService.windowMenuNames`.

## Verification notes

- Build verified with Java 25 preview enabled:

  ```bash
  javac --release 25 --enable-preview -d out $(find . -name '*.java' -print)
  ```

- **Caveat:** `util/DateUtil.jav` uses `.jav` extension, so wildcard `*.java` build commands will not compile it unless renamed or explicitly included.

## Conclusion

All requested features are represented in the project, with one practical caveat about the `DateUtil.jav` filename extension.
