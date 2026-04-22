package restaurant.service;

import restaurant.model.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class MenuService {

    private final List<MenuItem> menu = new ArrayList<>();
    private final String modeLabel;

    public MenuService() {
        this.modeLabel = "default";
        menu.add(new MenuItem(UUID.randomUUID().toString(), "Pizza", Category.MAIN, 12.99, true));
        menu.add(new MenuItem(UUID.randomUUID().toString(), "Burger", Category.MAIN, 9.99, true));
        menu.add(new MenuItem(UUID.randomUUID().toString(), "Ice Cream", Category.DESSERT, 5.99, false));
        menu.add(new MenuItem(UUID.randomUUID().toString(), "Coke", Category.DRINK, 2.99, true));
    }

    public MenuService(String modeInput) {
        modeInput = modeInput == null ? "default" : modeInput.trim();
        this();
        System.out.println("Menu Service Mode: " + modeInput);
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public String getModeLabel() {
        return modeLabel;
    }

    public void sortByPrice() {
        menu.sort(Comparator.comparing(MenuItem::price)
                .thenComparing(MenuItem::name));
    }

    public void demonstrateLambdas() {
        System.out.println("\nLambdas:");

        Consumer<MenuItem> printer = item -> System.out.println(item.name());

        Predicate<MenuItem> available = MenuItem::available;

        Function<MenuItem, Double> priceExtractor = MenuItem::price;

        Supplier<String> idGenerator = () -> UUID.randomUUID().toString();

        menu.stream()
                .filter(available)
                .forEach(printer);

        double totalPrice = menu.stream()
                .map(priceExtractor)
                .reduce(0.0, Double::sum);

        System.out.println("Total Price of Menu Items: " + totalPrice);

        System.out.println("Generated ID: " + idGenerator.get() + "\n");
    }

    public void demonstrateCollectors() {

        var grouped = menu.stream()
                .collect(Collectors.groupingBy(MenuItem::category));

        var partitioned = menu.stream()
                .collect(Collectors.partitioningBy(MenuItem::available));

        var toMap = menu.stream()
                .collect(Collectors.toMap(MenuItem::id, MenuItem::name));

        System.out.println("Collectors:");
        System.out.println("Grouped by category: " + grouped + "\n");
        System.out.println("Partitioned by availability: " + partitioned + "\n");
        System.out.println("Mapped by ID: " + toMap + "\n\n");
    }

    public void demonstrateIntermediateOps() {
        List<String> processedNames = menu.stream()
                .map(MenuItem::name)
                .distinct()
                .sorted()
                .limit(3)
                .toList();

        System.out.println("Intermediate Operations:");
        System.out.println("Distinct Sorted Menu Names (limit 3): " + processedNames + "\n");
    }
}
