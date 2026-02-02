package interfaces;

public class TransferPayment implements PaymentMethod {

    double comission = 10;

    @Override
    public void pay(double quantity) {
        System.out.println("Paying with card");
        quantity -= comission;
        System.out.println("Comission: $" + comission);
        System.out.println("Total: " + quantity);
    }
}
