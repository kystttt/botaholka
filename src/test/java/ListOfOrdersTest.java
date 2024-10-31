import order.ListOfOrders;
import order.Order;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


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
     * Тест возвращения списка всех заказов
     */
    @Test
    void testGetHashMap() {
        Order order1 = new Order((long)1);
        Order order2 = new Order((long)1);
        listOfOrders.putOrder(order1);
        listOfOrders.putOrder(order2);

        HashMap<Integer, Order> result = listOfOrders.getHashMap();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(order1, result.get(1));
        Assertions.assertEquals(order2, result.get(2));
    }

    /**
     * Тестирует возвращение существующего элемента
     */
    @Test
    void testGetValue() {
        Order order = new Order((long)1);
        listOfOrders.putOrder(order);

        Order result = listOfOrders.getValue(1);

        Assertions.assertEquals(order, result);
    }

    /**
     * Тест добавления заказа
     */
    @Test
    void testPutOrder() {
        Order order = new Order((long)1);

        listOfOrders.putOrder(order);

        Assertions.assertEquals(order, listOfOrders.getValue(1));
    }

    /**
     * Тест удаления элемента
     */
    @Test
    void testRemoveById() {
        Order order = new Order((long)1);
        listOfOrders.putOrder(order);

        listOfOrders.removeById(1);

        Assertions.assertNull(listOfOrders.getValue(1));
    }
}
