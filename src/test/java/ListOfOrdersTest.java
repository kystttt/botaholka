import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;


public class ListOfOrdersTest {

    private ListOfOrders listOfOrders = new ListOfOrders();
    /**
     * Обнуляет ListOfOrders
     */
    @BeforeEach
    void resetListOfOrders() {
        listOfOrders.clearList();
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
    void testGetValue1() {
        Order order = new Order((long)1);
        listOfOrders.putOrder(order);

        Order result = listOfOrders.getValue(1);

        Assertions.assertEquals(order, result);
    }

    /**
     * Тестирует возвращение несуществующего элемента
     */
    @Test
    void testGetValue2() {
        Order result = listOfOrders.getValue( 1);

        Assertions.assertNull(result);
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
