import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfOrders_testGetHashMap {
    /**
     * Тест возвращения списка всех заказов
     */
    @Test
    void testGetHashMap() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order1 = new Order((long)1);
        Order order2 = new Order((long)1);
        list.putOrder(order1);
        list.putOrder(order2);

        HashMap<Integer, Order> result = list.getHashMap();

        assertEquals(2, result.size());
        assertEquals(order1, result.get(1));
        assertEquals(order2, result.get(2));
    }
}
