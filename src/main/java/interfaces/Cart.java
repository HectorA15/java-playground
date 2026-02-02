package interfaces;

public class Cart {
    private PaymentMethod paymentMethod;

    public Cart(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void buyProducts(double total) {
        paymentMethod.pay(total);
    }

}
