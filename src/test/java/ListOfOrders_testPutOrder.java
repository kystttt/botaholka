import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfOrders_testPutOrder {
    /**
     * Тест добавления заказа
     */
    @Test
    void testPutOrder() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);

        list.putOrder(order);

        assertEquals(order, list.getValue(1));
    }
}
