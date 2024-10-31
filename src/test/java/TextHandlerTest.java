import menu.*;
import order.ListOfOrders;
import order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для TextHandler
 */
public class TextHandlerTest{
    private Cart cart;
    private TextHandler textHandler;
    private ListOfOrders listOfOrders;
    private Menu menu;
    Long chat_id = 13245L;


    @BeforeEach
    public void setup() {
        cart = new Cart();
        menu = new MenuImpl();
        listOfOrders = new ListOfOrders();
        textHandler = new TextHandler(listOfOrders, cart, menu);
        menu.addFoodItem("Шаурма", 220);
        menu.addFoodItem("Напиток", 110);
    }


    @Test
    public void testAddToCart_ValidDishIndex() {
        textHandler.processMessage("/menu", chat_id);
        textHandler.processMessage("Шаурма", chat_id);
        assertEquals("Ваш заказ:\n1. Шаурма - 220 рублей\n",
                textHandler.processMessage("/cart",chat_id));
        textHandler.processMessage("/menu", chat_id);
        assertEquals(1, cart.getCartSize());
        assertEquals("Блюдо добавлено в корзину:\nШаурма - 220 рублей\nПосмотреть вашу корзину /cart",
                textHandler.processMessage("Шаурма",chat_id));
        textHandler.processMessage("/delete", chat_id);
        textHandler.processMessage("1", chat_id);
        textHandler.processMessage("1", chat_id);
        assertEquals(0, cart.getCartSize());
        assertEquals("Корзина пуста", textHandler.processMessage("/cart", chat_id));
    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        textHandler.processMessage("/menu", chat_id);
        textHandler.processMessage("5", chat_id);
        assertEquals(0, cart.getCartSize());
        assertEquals("Ошибка: такого блюда нет в меню.", textHandler.processMessage("5", chat_id));
    }


    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder(){
        textHandler.processMessage("/menu", chat_id);
        textHandler.processMessage("Шаурма", chat_id);
        String expected = textHandler.processMessage("/order", chat_id);
        assertEquals("Ваш заказ сформирован", expected);
        assertEquals(0, cart.getCartSize());
        textHandler.processMessage("/delete", chat_id);
        textHandler.processMessage("0", chat_id);
        assertEquals("Корзина пуста", textHandler.processMessage("/order",chat_id));
    }

    /** Тест для команды удаления заказа из ListOfOrders
         */
    @Test
    void cancelOrderTest(){
        TextHandler textHandler = new TextHandler(listOfOrders, cart, menu);

        Order order = new Order((long)1);
        order.addToArr("Шаурма");
        listOfOrders.putOrder(order);

        String result_text = textHandler.processMessage("/cancel 1", (long)1);
        String expected_text = "Заказ №" + (long)1 + " удалён ";

        Assertions.assertEquals(expected_text, result_text);
        Assertions.assertNull(listOfOrders.get(1));
    }

    /**
     * Тест для команды дублирования определенного заказа
     */
    @Test
    void commandDuplicateTest(){
        TextHandler textHandler = new TextHandler(listOfOrders, cart, menu);

        Order order = new Order((long)1);
        order.addToArr("Шаурма");
        listOfOrders.putOrder(order);

        textHandler.processMessage("/duplicate 1", (long)1);

        Assertions.assertEquals(order, listOfOrders.get(1));
        Assertions.assertEquals(2, listOfOrders.size());
    }

    /**
     *Тест для команды отображения текущих заказов
     */
    @Test
    void commandListOfOrdersTest(){
        TextHandler textHandler = new TextHandler(listOfOrders, cart, menu);

        Order order = new Order((long)1);
        order.addToArr("Шаурма");
        listOfOrders.putOrder(order);

        String result = textHandler.processMessage("/listoforders", (long)1);

        String expected = """
                Ваши заказы:
                Заказ №1
                Шаурма - 220 руб.
                Итого: 220 руб.

                Ваши функции:
                /duplicate “Номер заказа” - повторить заказ
                /cancel “Номер заказа” - отменить заказ
                """;
        Assertions.assertEquals(expected, result);
    }

}



