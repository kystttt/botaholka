import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListOfOrders_testGetValue1 {
    /**
     * Тестирует возвращение существующего элемента
     */
    @Test
    void testGetValue1() {
        ListOfOrders list = ListOfOrders.INSTANCE;
        Order order = new Order((long)1);
        list.putOrder(order);

        Order result = list.getValue(1);

        assertEquals(order, result);
    }
}
