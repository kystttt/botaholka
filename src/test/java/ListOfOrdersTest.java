import org.junit.jupiter.api.BeforeEach;
import urfu.Order;
import urfu.ListOfOrders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


public class ListOfOrdersTest {

    /**
     * Обнуляет ListOfOrders
     */
    @BeforeEach
    void resetListOfOrders() {
        ListOfOrders.INSTANCE.clearList();
    }


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

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(order1, result.get(1));
        Assertions.assertEquals(order2, result.get(2));
    }

    /**
     * Тестирует возвращение существующего элемента
     */
    @Test
    void testGetValue1() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        list.putOrder(order);

        Order result = list.getValue(1);

        Assertions.assertEquals(order, result);
    }

    /**
     * Тестирует возвращение несуществующего элемента
     */
    @Test
    void testGetValue2() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order result = list.getValue( 1);

        Assertions.assertNull(result);
    }

    /**
     * Тест добавления заказа
     */
    @Test
    void testPutOrder() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);

        list.putOrder(order);

        Assertions.assertEquals(order, list.getValue(1));
    }

    /**
     * Тест удаления элемента
     */
    @Test
    void testRemoveById() {
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        list.putOrder(order);

        list.removeById(1);

        Assertions.assertNull(list.getValue(1));
    }
}
