# Restaurant Order and Analytics System (SROAS)

## Overview

A Java 25 console application that simulates a restaurant ordering workflow. This project demonstrates modern and advanced Java language features while providing a functional system for browsing a menu, placing orders, processing them concurrently, and persisting order records to disk.

**Purpose**: Enable menu browsing, item selection, payment processing, kitchen order management, and file-based order persistence — while showcasing Java 25 fundamentals, advanced features, and preview JEPs.

**Current State**: Fully functional restaurant system with multilingual support, sealed payment types, concurrent kitchen processing, NIO2 file I/O, Scoped Values, and Stream Gatherers.

---

## Project Architecture

### Structure

```
sroas/
├── RestaurantApp.java              # Entry point — interactive CLI, Scoped Values, instance main (JEP 512)
├── model/
│   ├── MenuItem.java               # Record — menu item (name, price, category, availability)
│   ├── Order.java                  # Record — order (items, payment, timestamp)
│   ├── Payment.java                # Sealed interface — payment hierarchy root
│   ├── CashPayment.java            # Sealed permit — cash payment
│   ├── CardPayment.java            # Sealed permit — card payment
│   └── OnlinePayment.java          # Sealed permit — online payment
├── service/
│   ├── MenuService.java            # Sorting, lambdas, streams, collectors, intermediate ops
│   ├── AnalyticsService.java       # Stream terminal operations (min, max, count, findAny, match...)
│   ├── GathererService.java        # Java 25 Stream Gatherers (JEP)
│   ├── OrderService.java           # Order creation, pattern matching for switch
│   ├── KitchenService.java         # Concurrency — ExecutorService + Callable
│   └── FileService.java            # NIO2 — Files, Path, StandardOpenOption
├── util/
│   ├── LocaleManager.java          # Localisation — ResourceBundle, Locale
│   └── DateUtil.jav                # Date/Time API — LocalDateTime, ZonedDateTime, DateTimeFormatter
├── messages_en.properties          # English locale strings
└── messages_es.properties          # Spanish locale strings
```

---

## Key Features

### Restaurant Operations

- Browse menu sorted by price
- Select items and specify payment method
- Create and timestamp orders
- Concurrent kitchen order processing
- Persist orders to disk and read them back

### Payment Types (Sealed Hierarchy)

- **Cash** — CashPayment
- **Card** — CardPayment (default)
- **Online** — OnlinePayment

### Multilingual Interface

- English and Spanish supported at startup via `ResourceBundle`
- All user-facing prompts and messages are externalised to `.properties` files

---

## Java Concepts Demonstrated

### Fundamentals

- **Sorting** — `Comparator.comparing(MenuItem::price)` in `MenuService.sortByPrice()`
- **Lambdas** — `Consumer`, `Predicate`, `Supplier`, `Function` in `MenuService.demonstrateLambdas()`
- **Stream terminal operations** — `min()`, `max()`, `count()`, `findAny()`, `findFirst()`, `allMatch()`, `anyMatch()`, `noneMatch()`, `forEach()` in `AnalyticsService.runAnalytics()`
- **Stream collectors** — `Collectors.toMap()`, `groupingBy()`, `partitioningBy()` in `MenuService.demonstrateCollectors()`
- **Stream intermediate operations** — `filter()`, `distinct()`, `limit()`, `map()`, `sorted()` in `MenuService.demonstrateIntermediateOps()`
- **Switch expressions & pattern matching** — `RestaurantApp.parsePayment()` and `OrderService.paymentType()`
- **Sealed classes and interfaces** — `Payment` sealed interface with three permitted implementations
- **Date/Time API** — `LocalDateTime`, `ZonedDateTime`, `DateTimeFormatter`, `ZoneId` in `Order` and `DateUtil`
- **Records** — `MenuItem` and `Order` as immutable data carriers

### Advanced

- **Concurrency** — `ExecutorService` with `invokeAll()` and `Callable` in `KitchenService.processOrders()`
- **NIO2** — `Files.write()`, `Files.readAllLines()`, `Path`, `Paths`, `StandardOpenOption` in `FileService`
- **Localisation** — `ResourceBundle`, `Locale`, externalised `.properties` files in `LocaleManager`
- **JEP 512 — Compact source files + instance main methods** — instance `void main()` in `RestaurantApp`
- **JEP 513 — Flexible constructor bodies** — pre-`this()` logic in `MenuService(String modeInput)`

### Extra (Java 25)

- **Scoped Values** — `ScopedValue<String> USER` with `ScopedValue.where(...).run(...)` in `RestaurantApp`
- **Stream Gatherers** — custom windowing `Gatherer` in `GathererService.windowMenuNames()`

---

## How to Run

> **Requires Java 25 with preview features enabled.**

### Compile

```bash
javac --release 25 --enable-preview -d out \
  RestaurantApp.java \
  model/*.java \
  service/*.java \
  util/LocaleManager.java
```

### Run

```bash
java --enable-preview -cp out restaurant.RestaurantApp
```

### Sample Session

```
Choose language (en/es, default: en): es
¡Bienvenido al Sistema de Restaurante!
Enter your name (default: Guest): Mukund
Current user: Mukund

1. Pasta Carbonara   - €12.50  [Available]
2. Margherita Pizza  - €10.00  [Available]
3. Caesar Salad      - €8.50   [Available]

Enter selection (e.g. 1,2): 1,3
Enter payment method (cash/card/online): card
Order placed. Kitchen processing...
Order saved to orders.txt
```

---

## Technical Details

- **Language**: Java 25 (preview features required)
- **Dependencies**: None (Java Core API only)
- **Packages Used**: `java.util`, `java.util.concurrent`, `java.util.function`, `java.time`, `java.nio.file`, `java.lang`

### Design Patterns

- Sealed class hierarchy for payment types
- Record-based immutable data models
- Service-layer separation of concerns
- Repository-style in-memory menu management

---

## Assignment Coverage

This project was submitted as the OOP-II Module Assignment (30%), demonstrating all required fundamental and advanced Java features, plus both extra-mark Java 25 features.