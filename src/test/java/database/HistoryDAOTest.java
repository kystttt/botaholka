package database;

import database.core.DB;
import database.core.HistoryDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.order.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Тесты для HistoryDAO
 */
public class HistoryDAOTest {
    private final HistoryDAO historyDAO;

    public HistoryDAOTest() {
        DB db = new DB("jdbc:h2:mem:test", "sa", "");
        historyDAO = new HistoryDAO(db);
    }

    /**
     * Создание таблицы history
     */
    private void createHistoryTable(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");

            String tableQuery = "CREATE TABLE history " +
                    "(id SERIAL primary key, chat_id INT, order_id INT, items TEXT, sum INT); ";

            connection.createStatement().executeUpdate(tableQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу/вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Удаление таблицы history
     */
    private void deleteTable() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            connection.createStatement().executeUpdate("drop table history;");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицы\n" + e);
        }
    }

    /**
     * Добавление строки(chatId, orderId, orderItems, orderSum) в таблицу history
     * @param chatId id пользователя
     * @param order заказ пользователя
     */
    private void insertInTable(Long chatId, Order order) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            String customerEntryQuery = "INSERT INTO history(chat_id, order_id, items, sum) " +
                    "VALUES (" + chatId + ", " + order.getId() + ", '" + order.itemsToString() + "', " + order.getSum() + ");";
            connection.createStatement().executeUpdate(customerEntryQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Проверка получения элементов из таблицы history
     */
    @Test
    public void getOrdersTest(){
        createHistoryTable();
        Order order1 = new Order(1, 1, 123L, List.of("test1", "test2"));
        Order order2 = new Order(1, 2, 123L, List.of("test4", "test3"));
        insertInTable(123L, order1);
        insertInTable(123L, order2);

        List<Order> actual = historyDAO.getOrders(123L);
        List<Order> expected = List.of(order1, order2);
        Assertions.assertEquals(expected, actual);

        actual = historyDAO.getOrders(12L);
        Assertions.assertEquals(List.of(), actual);

        deleteTable();
    }

    /**
     * Проверка добавления заказов в таблицу history
     */
    @Test
    public void addOrder(){
        createHistoryTable();
        Order order1 = new Order(1, 1, 12L, List.of("test1", "test2"));

        historyDAO.addOrder(order1.getChatId(), order1);

        List<Order> actual = historyDAO.getOrders(12L);
        List<Order> expected = List.of(order1);
        Assertions.assertEquals(expected, actual);

        deleteTable();
    }
}
