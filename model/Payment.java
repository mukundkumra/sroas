package restaurant.model;

public sealed interface Payment
        permits CardPayment, CashPayment, OnlinePayment {

    double processPayment(double amount);
}