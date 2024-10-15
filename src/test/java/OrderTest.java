import org.junit.jupiter.api.Test;
import urfu.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {
    @Test
    public void testFormSum(){
        Order order = new Order((long)123);
        order.addToArr("Шаурма Большая");
        order.formSum();
        assertEquals(100, order.sum);
    }


}
