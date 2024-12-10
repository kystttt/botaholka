package database.core;

import database.api.DataBase;
import utils.order.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataBase implements DataBase<Order> {
    DB db;
    private final String tableName;

    public HistoryDataBase(String tableName){
        this.tableName = tableName;
        db = new DB(tableName);
    }

    @Override
    public List<Order> get(Long chatId) {
        List<Order> result = new ArrayList<>();

        int response = db.getConnection();
        if(response == 0){
            return result;
        }

        String query = "select * " +
                "from " + tableName + " " +
                "where chat_id = " + chatId + ";";

        try{
            ResultSet resultSet = db.executeQuery(query);
            while(resultSet.next()){
                int orderId = resultSet.getInt("order_id");
                int sum = resultSet.getInt("sum");
                ArrayList<String> items = new ArrayList<>(List.of(resultSet.getString("items").split("/")));

                Order order = new Order(sum, orderId, chatId, items);
                result.add(order);
            }
        }catch (SQLException e){
            System.out.println("Ошибка выполнения запроса для таблицы истории\n" + e);
        }
        db.closeConnection();
        return result;
    }

    @Override
    public int set(Long chatId, Order order) {
        int response = db.getConnection();
        if(response == 0){
            return  0;
        }

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

        response = db.executeUpdate(query);
        db.closeConnection();
        if(response == 0){
            return 0;
        }
        return 1;
    }

    @Override
    public void createTestTable() {
        db.createHistoryTestTable();
    }
}
