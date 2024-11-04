package order;

import java.util.HashMap;
import java.util.List;


/**
 * Список всех текущих заказов
 */
public class ListOfOrders implements Orders {


    @Override
    public void putOrder(Order order) {

    }

    @Override
    public List<Order> getOrders() {
        return List.of();
    }

    @Override
    public Order get(int orderId) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}