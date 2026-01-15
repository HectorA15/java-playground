package collections.restaurantExample;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        TODO: Create an application that manages restaurant orders
            - The system must: Receive orders (with a unique ID) and add them to the service queue.
            - Validate that the IDs are not duplicated using a Set.
            - Follow orders following the principle "first in, first out" (Queue).
            - List orders still pending.
            - Cancel a specific order and verify the queue update.
         */

        restaurantManager manager = new restaurantManager();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            CLI();
            System.out.print("Select an option: ");
            if (scanner.hasNextLong()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addOrder(manager, scanner);
                    case 2 -> manager.attendOrder();
                    case 3 -> cancelOrder(manager, scanner);
                    case 4 -> manager.listOrders();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } else {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }

    private static void CLI() {
        System.out.println("======= Restaurant Management System  =======");
        System.out.println("1. Add Order");
        System.out.println("2. Attend following order");
        System.out.println("3. Cancel order");
        System.out.println("4. List pending orders");
        System.out.println("5. Exit");
    }

    private static void addOrder(restaurantManager manager, Scanner scanner) {
        System.out.println("Enter order details: ");
        String name = scanner.nextLine();
        manager.addOrder(name);
    }

    private static void cancelOrder(restaurantManager manager, Scanner scanner) {
        long id;
        while (true) {
            System.out.print("Enter order ID to cancel: ");
            if (scanner.hasNextLong()) {
                id = scanner.nextLong();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid ID. Use numbers only.");
                scanner.nextLine();
            }
        }
        manager.cancelOrder(id);
    }


}


