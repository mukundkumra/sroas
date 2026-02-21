package restaurant;

import restaurant.model.*;
import restaurant.service.*;
import restaurant.util.LocaleManager;

import java.lang.ScopedValue;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

class RestaurantApp {

    static final ScopedValue<String> USER = ScopedValue.newInstance();

    void main() throws Exception {

        ScopedValue.where(USER, "Manager")
                .run(() -> System.out.println("Current User: " + USER.get()));

        LocaleManager locale = new LocaleManager();
        locale.printWelcome("en");

        MenuService menuService = new MenuService();
        menuService.sortByPrice();
        menuService.demonstrateLambdas();
        menuService.demonstrateCollectors();

        AnalyticsService analytics = new AnalyticsService();
        analytics.runAnalytics(menuService.getMenu());

        OrderService orderService = new OrderService();

        Order order = orderService.createOrder(
                menuService.getMenu().stream().limit(2).toList(),
                new CardPayment());

        KitchenService kitchen = new KitchenService();
        kitchen.processOrders(List.of(order));

        FileService fileService = new FileService();
        fileService.saveOrder(order);
        fileService.readOrders();

        // Stream Gatherers
        Stream.of(1, 2, 3, 4, 5)
                .gather(Gatherers.windowFixed(2))
                .forEach(System.out::println);
    }
}