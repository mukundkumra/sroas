package restaurant.model;

public final class CashPayment implements Payment {

    @Override
    public double processPayment(double amount) {
        return amount * 0.98; // 2% discount
    }
}