package database.core;

import utils.order.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Управление таблицей истории заказов
 */
public class HistoryDAO {
    DB db;
    private final String tableName = "history";

    public HistoryDAO(DB db) {
        this.db = db;
    }

    /**
     * Получает список заказов для пользователя по его id
     * из таблицы истории
     * @param chatId id пользователя
     * @return список заказов для пользователя по его id
     */
    public List<Order> getOrders(Long chatId) {
        List<Order> result = new ArrayList<>();

        String query = "select * " +
                "from " + tableName + " " +
                "where chat_id = " + chatId + ";";

        try{
            ResultSet resultSet = db.executeQuery(query);
            try{
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int sum = resultSet.getInt("sum");
                    List<String> items = List.of(resultSet.getString("items").split("/"));

                    Order order = new Order(sum, orderId, chatId, items);
                    result.add(order);
                }
            }catch(SQLException e){
                System.out.println("Один из столбцов недоступен в таблице истории\n" + e);
            }
        }catch (SQLException e){
            System.out.println("Не смог обратиться к бд, таблице истории\n" + e);
        }

        return result.stream().toList();
    }

    /**
     * Добавляет в таблицу истории заказ по id пользователя
     * @param chatId id пользователя
     * @param order  заказ пользователя
     */
    public void addOrder(Long chatId, Order order) {
        int orderId = order.getId();
        String text = order.itemsToString();
        int sum = order.getSum();

        String query = "insert into " +
                tableName +
                "(chat_id, order_id, items, sum) " +
                "values (" +
                chatId + ", " +
                orderId + ", '" +
                text + "', " +
                sum + ");";

        try{
            db.executeUpdate(query);
        }catch (SQLException e){
            System.out.println("Не удалось добавить заказ в таблицу history\n" + e);
        }
    }
}
