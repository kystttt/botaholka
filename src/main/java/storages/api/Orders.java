package storages.api;

import order.Order;
import storages.core.ListOfOrders;

import java.util.List;

public interface Orders{

    /**
     * Создание нового {@link Order} в {@link ListOfOrders}
     * @param order новый заказ
     */
    void put(Order order);

    /**
     * Возвращает список всех активных {@link Order}
     */
    List<Order> getOrders();

    /**
     * Возвращает {@link Order} по его id
     */
    Order get(int orderId);

    /**
     * Удаляет {@link Order} по его id
     */
    void remove(int id);

    /**
     * Возвращает размер корзины
     */
    int size();
}