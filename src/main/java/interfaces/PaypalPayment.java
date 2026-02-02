package interfaces;

public class PaypalPayment implements PaymentMethod {

    double comission = 0.05;

    @Override
    public void pay(double quantity) {
        System.out.println("Paying with card");
        double total = quantity - quantity * comission;
        System.out.println("Comission: " + quantity * comission);
        System.out.println("Total: " + total);
    }
}
