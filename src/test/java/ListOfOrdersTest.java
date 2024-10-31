import order.ListOfOrders;
import order.Order;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListOfOrdersTest {

    private ListOfOrders listOfOrders;

    /**
     * Обнуляет order.ListOfOrders
     */
    @BeforeEach
    void resetListOfOrders() {
        listOfOrders = new ListOfOrders();
    }

    /**
     * Тест добавления элемента
     */
    @Test
    void testPutOrder() {
        Order order1 = new Order((long)1);
        Order order2 = new Order((long)2);
        listOfOrders.putOrder(order1);
        listOfOrders.putOrder(order2);

        Assertions.assertEquals(2, listOfOrders.size());
        Assertions.assertEquals(order1, listOfOrders.get(1));
        Assertions.assertEquals(order2, listOfOrders.get(2));
    }

    /**
     * Тест на валидность присваивания Id для нового заказа
     */
    @Test
    void orderIdTest(){
        Order order1 = new Order((long)1);
        Order order2 = new Order((long)2);
        listOfOrders.putOrder(order1);
        listOfOrders.putOrder(order2);

        Assertions.assertEquals(1, listOfOrders.get(1).getId());
        Assertions.assertEquals(2, listOfOrders.get(2).getId());

        resetListOfOrders();

        Order order3 = new Order((long)3);
        listOfOrders.putOrder(order3);
        Assertions.assertEquals(1, listOfOrders.get(1).getId());
    }
}
