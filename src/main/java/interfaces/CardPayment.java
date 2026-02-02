package interfaces;

public class CardPayment implements PaymentMethod {

    double comission = 0.03;

    @Override
    public void pay(double quantity) {
        System.out.println("Paying with card");
        double total = quantity - quantity * comission;
        System.out.println("Comission: " + quantity * comission);
        System.out.println("Total: " + total);
    }
}
