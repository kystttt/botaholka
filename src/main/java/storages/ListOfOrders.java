package storages;

import order.Order;

import java.util.HashMap;


/**
 * Список всех текущих заказов
 * Key: orderId
 * Value: Order
 */
public class ListOfOrders extends HashMap<Integer, Order> {

    /**
     * Номер заказа
     */
    private Integer orderId = 1;

    /**
     * Создание нового Order в ListOrders
     * @param order новый заказ
     */
    public void putOrder(Order order) {
        order.setOrderId(orderId);
        put(orderId, order);
        orderId++;
    }
}