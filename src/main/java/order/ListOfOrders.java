package order;

import order.Order;

import java.util.List;

/**
 * Список всех текущих заказов
 */
public class ListOfOrders implements Orders{

    List<Order> items;

    private Integer orderId = 1;

    @Override
    public void putOrder(Order order) {
        order.setOrderId(orderId);
        items.add(order);
        orderId++;
    }

    @Override
    public List<Order> getOrders() {
        return items;
    }

    @Override
    public Order get(int orderId) {
        return items.get(orderId);
    }

    @Override
    public void remove(int id) {
        items.removeIf(order -> order.getId() == id);
    }
}