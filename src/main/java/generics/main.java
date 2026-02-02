package generics;

public class main {
    public static void main(String[] args) {

        Box<String> stringBox = new Box<>();
        stringBox.setValue("Blackhole");
        stringBox.getValue();
        System.out.println("This box have... a " + stringBox.getValue());

        Box<Integer> integerBox = new Box<>();
        integerBox.setValue(100);
        integerBox.getValue();
        System.out.println("This box have a number and is... " + integerBox.getValue());
    }
}
