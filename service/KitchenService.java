package restaurant.service;

import restaurant.model.Order;

import java.util.List;
import java.util.concurrent.*;

public class KitchenService {

    public KitchenService() {
        System.out.println("Kitchen Initialised");
    }

    public void processOrders(List<Order> orders) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = orders.stream()
                .map(order -> (Callable<String>) () -> {
                    Thread.sleep(1000);
                    return "Processed order: " + order.orderId();
                })
                .toList();

        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> f : results) {
            System.out.println(f.get());
        }

        executor.shutdown();
    }
}