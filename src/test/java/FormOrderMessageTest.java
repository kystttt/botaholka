import menu.MenuImpl;
import order.FormOrderMessage;
import order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormOrderMessageTest {

    @Test
    public void forClientTest(){
        MenuImpl menu = new MenuImpl();
        menu.addFoodItem("Шаурма Большая", 100);

        Order order = new Order((long)123);
        order.setOrderId(123);
        order.addToArr("Шаурма Большая");
        order.addToArr("Шаурма Большая");

        String result = new FormOrderMessage().forClient(order, menu);
        String expected = """
                Заказ №123
                Шаурма Большая - 100 руб.
                Шаурма Большая - 100 руб.
                Итого: 200 руб.
                """;

        Assertions.assertEquals(expected, result);
    }
}
