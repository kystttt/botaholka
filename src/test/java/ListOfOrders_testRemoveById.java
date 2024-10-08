import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class ListOfOrders_testRemoveById {
    /**
     * Тест удаления элемента
     */
    @Test
    void testRemoveById() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        list.putOrder(order);

        list.removeById(1);

        assertNull(list.getValue(1));
    }
}
