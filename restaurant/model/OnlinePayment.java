package restaurant.model;

public final class OnlinePayment implements Payment {

    @Override
    public double processPayment(double amount) {
        return amount * 1.02;
    }
}