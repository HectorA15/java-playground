package collections.restaurantExample;

import java.util.*;

public class restaurantManager {

    private final Set<Long> orderIds = new HashSet<>();
    private final Queue<Order> queue = new LinkedList<>();
    private long nextId = 100;

    public void addOrder(String name) {
        Order newOrder = createOrder(name);

        if (orderIds.add(newOrder.getId())) {
            queue.add(newOrder);
            System.out.println("Order " + newOrder.getId() + ": " + newOrder.getName() + " added to queue.");
        } else {
            System.out.println("Error: The order " + newOrder.getId() + " already exists.");
        }
    }

    public void cancelOrder(long orderId) {
        if (orderIds.remove(orderId)) {
            queue.removeIf(order -> order.getId() == orderId);
            System.out.println("Order " + orderId + " cancelled successfully.");
        } else {
            System.out.println("Error: Order " + orderId + " does not exist.");
        }
    }

    public void attendOrder() {
        if (!queue.isEmpty()) {
            long orderId = queue.remove().getId();
            orderIds.remove(orderId);
            System.out.println("Processing order " + orderId);
        } else {
            System.out.println("No orders to process.");
        }
    }

    public List<Order> listOrders() {
        if (queue.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            queue.forEach(o -> System.out.println("Order " + o.getId() + ": " + o.getName()));
        }
        return List.copyOf(queue);
    }

    public Order createOrder(String name) {
        return new Order(nextId++, name);
    }

    public Order getLastOrder() {
        return queue.peek();
    }

}
