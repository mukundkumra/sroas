package restaurant.service;

import restaurant.model.MenuItem;

import java.util.Comparator;
import java.util.List;

public class AnalyticsService {

    public void runAnalytics(List<MenuItem> items) {

        items.stream().min(Comparator.comparing(MenuItem::price))
                .ifPresent(i -> System.out.println("Cheapest: " + i.name()));

        items.stream().max(Comparator.comparing(MenuItem::price))
                .ifPresent(i -> System.out.println("Most Expensive: " + i.name()));

        System.out.println("Count: " + items.stream().count());
        System.out.println("Any available: " + items.stream().anyMatch(MenuItem::available));
        System.out.println("None over 100: " + items.stream().noneMatch(i -> i.price() > 100));
        items.stream().findFirst().ifPresent(i -> System.out.println("First: " + i.name()));
    }
}