package restaurant.service;

import restaurant.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {

    public Order createOrder(List<MenuItem> items, Payment payment) {
        return new Order(
                UUID.randomUUID().toString(),
                items,
                LocalDateTime.now(),
                payment);
    }

    public String paymentType(Payment payment) {
        return switch (payment) {
            case CardPayment _ -> "Card";
            case CashPayment _ -> "Cash";
            case OnlinePayment _ -> "Online";
        };
    }
}