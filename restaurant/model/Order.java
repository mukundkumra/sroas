package restaurant.model;

import java.time.LocalDateTime;
import java.util.List;

public record Order(String orderId, List<MenuItem> items, LocalDateTime orderTime, Payment payment) {
    public double totalAmount() {
        return items.stream().mapToDouble(MenuItem::price).sum();
    }
}