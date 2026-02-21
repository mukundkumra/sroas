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

    private void runInteractive() throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter user name (default: Manager): ");
            String userInput = scanner.nextLine().trim();
            String user = userInput.isEmpty() ? "Manager" : userInput;

            MenuService menuService = new MenuService();
            menuService.sortByPrice();

            ScopedValue.where(USER, user)
                    .run(() -> System.out.println("Current User: " + USER.get()));

            LocaleManager locale = new LocaleManager();
            locale.printWelcome("en");

            List<MenuItem> menu = menuService.getMenu();
            System.out.println("Select items by number (comma-separated), e.g. 1,3:");
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.get(i);
                System.out.printf("%d) %s - %.2f (%s)%n", i + 1, item.name(), item.price(),
                        item.available() ? "available" : "unavailable");
            }

            System.out.print("Your selection: ");
            String selection = scanner.nextLine();
            List<MenuItem> selectedItems = parseSelection(selection, menu);
            if (selectedItems.isEmpty()) {
                selectedItems = menu.stream().limit(2).toList();
                System.out.println("No valid selection found. Using first two menu items.");
            }

            System.out.print("Payment type (card/cash/online, default: card): ");
            Payment payment = parsePayment(scanner.nextLine());

            OrderService orderService = new OrderService();
            Order order = orderService.createOrder(selectedItems, payment);

            KitchenService kitchen = new KitchenService();
            kitchen.processOrders(List.of(order));

            FileService fileService = new FileService();
            fileService.saveOrder(order);
            fileService.readOrders();
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
                    selected.add(menu.get(idx));
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
