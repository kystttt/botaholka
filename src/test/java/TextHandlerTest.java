import org.junit.jupiter.api.BeforeEach;
import urfu.Order;
import urfu.ListOfOrders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import urfu.TextHandler;

public class TextHandlerTest {

    /**
     * Обнуляет ListOfOrders
     */
    @BeforeEach
    void resetListOfOrders() {
        ListOfOrders.INSTANCE.clearList();
    }

    /**
     *Тест для команды отображения текущих заказов
     */
    @Test
    void commandListOfOrdersTest(){
        TextHandler textHandler = new TextHandler();
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        order.addToArr("Шаурма Большая");
        list.putOrder(order);

        textHandler.logic("/listoforders", (long)1);
        String result = textHandler.getOutputMassage();

        String expected = """
                Ваши заказы:
                Заказ №1
                Шаурма Большая
                Итого: 100 руб.
                
                Ваши функции:
                /duplicate-{“Номер заказа”} - повторить заказ
                /cancel-{“Номер заказа”} - отменить заказ
                """;
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест для команды дублирования определенного заказа
     */
    @Test
    void commandDuplicateTest(){
        TextHandler textHandler = new TextHandler();
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        order.addToArr("Шаурма Большая");
        list.putOrder(order);

        textHandler.logic("/duplicate-1", (long)1);

        Assertions.assertTrue(order.equals(list.getValue(2)));
    }

    /**
     * Тест для команды удаления заказа из ListOfOrders
     */
    @Test
    void commandCancelOrderTest(){
        TextHandler textHandler = new TextHandler();
        ListOfOrders list = ListOfOrders.INSTANCE;

        Order order = new Order((long)1);
        order.addToArr("Шаурма Большая");
        list.putOrder(order);

        textHandler.logic("/delete-1", (long)1);

        Assertions.assertNull(list.getValue(1));
    }
}
