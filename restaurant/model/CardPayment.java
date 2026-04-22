package restaurant.model;

public final class CardPayment implements Payment {

    @Override
    public double processPayment(double amount) {
        return amount;
    }
}