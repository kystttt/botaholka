import MenuLogic.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    public void messageForClientTest(){
        Menu menu = new Menu();
        menu.addFoodItem("Шаурма Большая", 100);

        Order order = new Order((long)123);
        order.setOrderId(123);
        order.addToArr("Шаурма Большая");
        order.addToArr("Шаурма Большая");

        String result = order.formMessageForClient(menu);
        String expected = """
                Заказ №123
                Шаурма Большая - 100 руб.
                Шаурма Большая - 100 руб.
                Итого: 200 руб.
                """;

        Assertions.assertEquals(expected, result);
    }
}
