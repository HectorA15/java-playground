package various;

public class EnumExample {

    public static void main(String[] args) {

        for (Color c : Color.values()) {
            System.out.println(c);
        }

        Color color = Color.BLUE;
        System.out.println(color.name());
        System.out.println(color.ordinal());


        try {
            color = Color.valueOf("Blu");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid color");
        }

        System.out.println(color);


    }


    public enum Color {
        RED, GREEN, BLUE, YELLOW, ORANGE, BLACK, WHITE
    }

}
