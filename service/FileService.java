package restaurant.service;

import restaurant.model.Order;

import java.io.IOException;
import java.nio.file.*;

public class FileService {

    private static final Path PATH = Paths.get("orders.txt");

    public void saveOrder(Order order) throws IOException {
        Files.writeString(PATH,
                order.toString() + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public void readOrders() throws IOException {
        if (Files.exists(PATH)) {
            System.out.println(Files.readString(PATH));
        }
    }
}