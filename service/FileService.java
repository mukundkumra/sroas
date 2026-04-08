package restaurant.service;

import restaurant.model.Order;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;

public class FileService {

    private static final Path PATH = Paths.get("orders.txt");

    public void saveOrder(Order order) throws IOException {
        Files.writeString(PATH,
                formatOrder(order) + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public void printLastSavedOrder() throws IOException {
        if (!Files.exists(PATH)) {
            return;
        }

        String content = Files.readString(PATH);
        if (content.isBlank()) {
            System.out.println("No saved orders yet.");
            return;
        }

        String[] lines = content.split("\\R");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (!lines[i].isBlank()) {
                System.out.println("Saved order: " + lines[i]);
                return;
            }
        }

        System.out.println("No saved orders yet.");
    }

    private String formatOrder(Order order) {
        String itemNames = order.items().stream()
                .map(item -> item.name() + " ($" + String.format("%.2f", item.price()) + ")")
                .collect(Collectors.joining(", "));

        return "{\"orderId\": " + order.orderId()
                + ", \"time\": " + order.orderTime()
                + ", \"payment\": \"" + order.payment().getClass().getSimpleName().replace("Payment", "")
                + "\", \"total\": " + String.format("%.2f", order.totalAmount())
                + ", \"items\": [" + itemNames + "]}";
    }
}
