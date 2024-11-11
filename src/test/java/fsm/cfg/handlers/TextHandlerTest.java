package fsm.cfg.handlers;

import menu.*;
import order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для TextHandler
 */
public class TextHandlerTest {
    private TextHandler textHandler;
    private Menu menu;
    Long chat_id = 13245L;

    @BeforeEach
    public void setup() {
        menu = new MenuImpl();
        menu.addFoodItem("Шаурма", 220);
        menu.addFoodItem("Напиток", 110);
        textHandler = new TextHandler(menu);
    }

    /**
     * Тест добавления блюда в корзину
     */
    @Test
    public void testAddToCart_ValidDishIndex() {

        textHandler.addToCart("1", chat_id);
        assertEquals("""
                        Ваш заказ:
                        1. Шаурма - 220 рублей
                        """,
                textHandler.viewCart(chat_id));

        String actual = textHandler.addToCart("1", chat_id);
        assertEquals(2, textHandler.cart.size());
        assertEquals("""
                        Блюдо добавлено в корзину:
                        Шаурма - 220 рублей
                        Посмотреть вашу корзину /cart""",
                actual);
    }

    /**
     * Тест удаления блюда из корзины
     */
    @Test
    public void testDeleteFromCart(){
        textHandler.addToCart("1", chat_id);

        String actual = textHandler.deleteFromCart("1", chat_id);

        assertEquals(0, textHandler.cart.size());
        assertEquals("Блюдо успешно удалено ", actual);
    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        String actual = textHandler.addToCart("5", chat_id);
        assertEquals(0, textHandler.cart.size());
        assertEquals("Ошибка: такого блюда нет в меню.", actual);
    }

    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder() {
        textHandler.addToCart("1", chat_id);

        String expected = textHandler.makeOrder(chat_id);
        assertEquals("Ваш заказ сформирован", expected);
        assertEquals(0, textHandler.cart.size());
        assertEquals(1, textHandler.listOfOrders.size());
    }

    /** Тест для команды удаления заказа из ListOfOrders
     */
    @Test
    void cancelOrderTest() {
        TextHandler textHandler = new TextHandler(menu);

        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        String result_text = textHandler.cancelOrder("1", (long) 1);
        String expected_text = "Заказ №" + (long) 1 + " удалён ";

        Assertions.assertEquals(expected_text, result_text);
        Assertions.assertNull(textHandler.listOfOrders.get(1));
    }

    /**
     * Тест для команды дублирования определенного заказа
     */
    @Test
    void duplicateTest() {
        TextHandler textHandler = new TextHandler(menu);

        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        textHandler.duplicate("1", 1);

        Assertions.assertEquals(order, textHandler.listOfOrders.get(1));
        Assertions.assertEquals(2, textHandler.listOfOrders.size());
    }

    /**
     * Тест для команды отображения текущих заказов
     */
    @Test
    void commandListOfOrdersTest() {
        TextHandler textHandler = new TextHandler(menu);

        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        String result = textHandler.listOfOrders((long) 1);

        String expected = """
                Ваши заказы:
                Заказ №1
                Шаурма - 220 руб.
                Итого: 220 руб.

                Доступные методы:
                /duplicate “Номер заказа” - повторить заказ
                /cancel “Номер заказа” - отменить заказ
                """;
        Assertions.assertEquals(expected, result);
    }
}