package collections.restarantExample;

import collections.restaurantExample.Order;
import collections.restaurantExample.restaurantManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class restaurantTest {

    private restaurantManager services;

    @BeforeEach
    public void setup() {
        services = new restaurantManager();
    }

    @Test
    public void testAddOrder() {
        services.addOrder("Burger");

        List<Order> orders = services.listOrders();
        assertEquals(1, orders.size(), "Debería haber 1 orden en la cola");
        assertEquals("Burger", services.getLastOrder().getName());
        assertEquals(100L, services.getLastOrder().getId(), "El primer ID debería ser 100");
    }

    @Test
    public void testCancelOrder() {
        services.addOrder("Burger"); // ID 100
        services.addOrder("Pizza");  // ID 101

        services.cancelOrder(100);

        List<Order> orders = services.listOrders();
        assertEquals(1, orders.size());
        assertEquals("Pizza", orders.get(0).getName(), "Solo debería quedar la Pizza");

        services.cancelOrder(999);
        assertEquals(1, services.listOrders().size());
    }

    @Test
    public void testAttendOrder() {
        services.addOrder("Burger");
        services.addOrder("Pizza");

        services.attendOrder();

        List<Order> orders = services.listOrders();
        assertEquals(1, orders.size());
        assertEquals("Pizza", orders.get(0).getName(), "La siguiente en atender debería ser la Pizza");
    }

    @Test
    public void testListOrdersEmpty() {
        List<Order> orders = services.listOrders();
        assertTrue(orders.isEmpty());
    }
}
