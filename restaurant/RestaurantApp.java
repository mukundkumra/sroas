package restaurant;

import restaurant.model.*;
import restaurant.service.*;
import restaurant.util.LocaleManager;

import java.lang.ScopedValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantApp {

    static final ScopedValue<String> USER = ScopedValue.newInstance();
    public static void main(String[] args) throws Exception {
        new RestaurantApp().runInteractive();
    }

    void main() throws Exception {
        runInteractive();
    }

    private void runInteractive() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            LocaleManager locale = new LocaleManager();

            System.out.print("Choose language (en/es, default: en): ");
            locale.setLocale(scanner.nextLine());
            locale.printWelcome();

            System.out.print(locale.text("prompt.user", locale.text("default.user")));
            String userInput = scanner.nextLine().trim();
            String user = userInput.isEmpty() ? locale.text("default.user") : userInput;

            MenuService menuService = new MenuService("  Standard Service Mode  ");
            menuService.sortByPrice();

            ScopedValue.where(USER, user)
                    .run(() -> System.out.println(locale.text("current.user", USER.get())));

            List<MenuItem> menu = menuService.getMenu();
            menuService.demonstrateLambdas();
            menuService.demonstrateCollectors();
            menuService.demonstrateIntermediateOps();

            AnalyticsService analyticsService = new AnalyticsService();
            analyticsService.runAnalytics(menu);

            GathererService gathererService = new GathererService();
            List<List<String>> windows = gathererService.windowMenuNames(
                    menu.stream().map(MenuItem::name).toList());
            System.out.println("Gatherers:");
            System.out.println("Gathered Windows: " + windows + "\n");

            System.out.println(locale.text("menu.selection.prompt"));
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.get(i);
                String availability = item.available()
                        ? locale.text("status.available")
                        : locale.text("status.unavailable");
                System.out.println(locale.text("menu.item.format",
                        i + 1, item.name(), item.price(), availability));
            }

            System.out.print(locale.text("prompt.selection"));
            String selection = scanner.nextLine();
            List<MenuItem> selectedItems = parseSelection(selection, menu);
            if (selectedItems.isEmpty()) {
                selectedItems = menu.stream().limit(2).toList();
                System.out.println(locale.text("selection.fallback"));
            }

            System.out.print(locale.text("prompt.payment"));
            Payment payment = parsePayment(scanner.nextLine());

            OrderService orderService = new OrderService();
            Order order = orderService.createOrder(selectedItems, payment);

            KitchenService kitchen = new KitchenService();
            kitchen.processOrders(List.of(order));

            FileService fileService = new FileService();
            fileService.saveOrder(order);
            fileService.printLastSavedOrder();
        }
    }

    private List<MenuItem> parseSelection(String input, List<MenuItem> menu) {
        List<MenuItem> selected = new ArrayList<>();
        for (String token : input.split(",")) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                int idx = Integer.parseInt(trimmed) - 1;
                if (idx >= 0 && idx < menu.size()) {
                    MenuItem item = menu.get(idx);
                    if (item.available()) {
                        selected.add(item);
                    } else {
                        System.out.println("Skipping unavailable item: " + item.name());
                    }
                }
            } catch (NumberFormatException ignored) {
                // Ignore invalid tokens
            }
        }
        return selected;
    }

    private Payment parsePayment(String input) {
        String normalized = input == null ? "" : input.trim().toLowerCase();
        return switch (normalized) {
            case "cash" -> new CashPayment();
            case "online" -> new OnlinePayment();
            default -> new CardPayment();
        };
    }
}
