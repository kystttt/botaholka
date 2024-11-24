import storages.core.ListOfOrders;
import order.Order;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListOfOrdersTest {

    private ListOfOrders listOfOrders;

    /**
     * Обнуляет storages.core.ListOfOrders
     */
    @BeforeEach
    void resetListOfOrders() {
        listOfOrders = new ListOfOrders();
    }

    /**
     * Тест добавления элемента
     */
    @Test
    void testPut() {
        Order order1 = new Order((long)1);
        Order order2 = new Order((long)2);
        listOfOrders.put(order1);
        listOfOrders.put(order2);

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
        listOfOrders.put(order1);
        listOfOrders.put(order2);

        Assertions.assertEquals(1, listOfOrders.get(1).getId());
        Assertions.assertEquals(2, listOfOrders.get(2).getId());

        resetListOfOrders();

        Order order3 = new Order((long)3);
        listOfOrders.put(order3);
        Assertions.assertEquals(1, listOfOrders.get(1).getId());
    }
}
