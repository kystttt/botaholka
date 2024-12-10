package database;

import database.api.DataBase;
import database.core.HistoryDataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.order.Order;
import utils.review.Review;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataBaseTest {
    DataBase<Order> historyDataBase = new HistoryDataBase("test");

    /**
     * Проверка получение и сохранении информации в базе данных
     */
    @Test
    void apiTest(){
        historyDataBase.createTestTable();
        ArrayList<String> items = new ArrayList<>(List.of("test1"));
        Order order = new Order(90, 1, 1L, items);
        int queryResponse = historyDataBase.set(1L, order);
        Assertions.assertEquals(1, queryResponse);
        List<Order> actual = historyDataBase.get(1L);
        Assertions.assertEquals(order, actual.getFirst());

    }
}
