package fsm.cfg.handlers;

import database.core.DB;
import menu.*;
import storages.api.Cart;
import utils.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для TextHandler
 */
public class TextHandlerTest {
    private TextHandler textHandler;
    private Menu menu;
    Long chat_id = 13245L;

    private final DB db = new DB("jdbc:h2:mem:test", "sa", "");;

    private void initializeBDTables(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");

            String tableQuery = "CREATE TABLE history " +
                    "(id SERIAL primary key, chat_id INT, order_id INT, items TEXT, sum INT); ";
            connection.createStatement().executeUpdate(tableQuery);
            tableQuery = "CREATE TABLE users " +
                    "(id SERIAL, state TEXT, chat_id INT primary key);";
            connection.createStatement().executeUpdate(tableQuery);
            String customerTableQuery = "CREATE TABLE reviews " +
                    "(id SERIAL primary key,chat_id INT, rating_5 INT, text TEXT); ";

            connection.createStatement().executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу/вставить данные в таблицу\n" + e);
        }
    }

    @BeforeEach
    public void setup() {
        menu = new MenuImpl();
        menu.addFoodItem("Шаурма", 220);
        menu.addFoodItem("Напиток", 110);

        textHandler = new TextHandler(menu, db);
        initializeBDTables();
    }

    /**
     * Тест добавления блюда в корзину
     */
    @Test
    public void testAddToCart_ValidDishIndex() {
        textHandler.addToCart("1", chat_id);

        assertEquals("""
            Ваша корзина:
            1. Шаурма - 220 рублей
            Доступные методы:
            /back - вернуться в меню
            /delete - удалить из корзины""",
                textHandler.viewCart(chat_id));

        String actual = textHandler.addToCart("1", chat_id);
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
    public void testDeleteFromCart() {
        textHandler.addToCart("1", chat_id);

        String actual = textHandler.deleteFromCart("1", chat_id);

        Cart userCart = textHandler.getCartForUser(chat_id);
        assertEquals(0, userCart.size(), "Корзина должна быть пуста после удаления");
        assertEquals("Блюдо успешно удалено ", actual, "Сообщение об удалении не совпадает");
    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        String actual = textHandler.addToCart("5", chat_id);

        Cart userCart = textHandler.getCartForUser(chat_id);
        assertEquals(0, userCart.size(), "Корзина должна быть пуста, так как блюдо не существует");
        assertEquals("Ошибка: такого блюда нет в меню.", actual, "Сообщение об ошибке не совпадает");
    }

    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder() {
        textHandler.addToCart("1", chat_id);

        String expected = textHandler.makeOrder(chat_id);
        assertEquals("Ваш заказ сформирован", expected);

        // Доступ к корзине через метод getCartForUser
        assertEquals(0, textHandler.getCartForUser(chat_id).size());  // Проверка размера корзины
        assertEquals(1, textHandler.listOfOrders.size());
    }

    /** Тест для команды удаления заказа из ListOfOrders
     */
    @Test
    void cancelOrderTest() {
        TextHandler textHandler = new TextHandler(menu, db);

        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        String result_text = textHandler.cancelOrder("1", 1);
        String expected_text = "Заказ №" + (long) 1 + " удалён ";

        Assertions.assertEquals(expected_text, result_text);
        Assertions.assertNull(textHandler.listOfOrders.get(1));
    }

    /**
     * Тест для команды дублирования определенного заказа
     */
    @Test
    void duplicateTest() {
        TextHandler textHandler = new TextHandler(menu, db);

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
        TextHandler textHandler = new TextHandler(menu, db);

        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        String result = textHandler.listOfOrders((long) 1);

        String expected = """
                Ваши заказы:
                Заказ №1
                Шаурма - 220 руб.
                Не принят
                Итого: 220 руб.

                Доступные методы:
                /duplicate “Номер заказа” - повторить заказ
                /cancel “Номер заказа” - отменить заказ
                /back - вернуться к назад
                """;
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест для команды смены статуса заказа
     */
    @Test
    void commandNextStatusTest(){
        TextHandler textHandler = new TextHandler(menu, db);
        Order order = new Order((long) 1);
        order.addToArr("Шаурма");
        textHandler.listOfOrders.put(order);

        String result = textHandler.nextStatus("1", 99);
        Assertions.assertEquals("Статус заказа изменён\n", result);

        String resultForInvalidOrder = textHandler.nextStatus("4", 99);
        Assertions.assertEquals("Такого заказа не существует\n", resultForInvalidOrder);

    }

    /**
     * Проверка получения и записи отзыва
     */
    @Test
    void reviewTest(){
        textHandler.reviewText("123", 1L);
        textHandler.rating(1L,"1");

        textHandler.insertReview(1L);

        String actual = textHandler.allReviews(1L);
        String expected = """
                1) 1
                123
                """;
        Assertions.assertEquals(expected, actual);
    }
}