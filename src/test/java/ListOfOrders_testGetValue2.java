import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ListOfOrders_testGetValue2 {
    /**
     * Тестирует возвращение несуществующего элемента
     */
    @Test
    void testGetValue2() {
        ListOfOrders list = ListOfOrders.INSTANCE;
        Order result = list.getValue( 1);

        assertNull(result);
    }
}
