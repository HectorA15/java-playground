package interfaces;

public class Main {
    public static void main(String[] args) {


        PaymentMethod card = new CardPayment();
        Cart cart = new Cart(card);
        cart.buyProducts(100);
        System.out.println();

        PaymentMethod paypal = new PaypalPayment();
        cart = new Cart(paypal);
        cart.buyProducts(100);
        System.out.println();

        PaymentMethod transfer = new TransferPayment();
        cart = new Cart(transfer);
        cart.buyProducts(100);
        System.out.println();

    }
}
