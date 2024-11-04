package order;

import java.util.List;

public interface Orders{

    /**
     * Создание нового {@link Order} в {@link ListOfOrders}
     * @param order новый заказ
     */
    void putOrder(Order order);

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
}