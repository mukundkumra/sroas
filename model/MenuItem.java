package restaurant.model;

public record MenuItem(
        String id,
        String name,
        Category category,
        double price,
        boolean available) {
}