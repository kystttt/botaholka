package order;

import java.util.ArrayList;
import java.util.List;

/**
 * Список всех текущих заказов
 */
public class ListOfOrders implements Orders {

    private final List<Order> items;

    private Integer orderId = 1;

    public ListOfOrders(){
        items = new ArrayList<>();
    }

    @Override
    public void put(Order order) {
        order.setOrderId(orderId);
        items.add(order);
        orderId++;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>(items);
    }

    @Override
    public Order get(int orderId) {
        for(Order order : items){
            if(orderId == order.getId()){
                return order;
            }
        }
        return null;
    }

    @Override
    public void remove(int id) {
        items.removeIf(order -> order.getId() == id);
    }

    @Override
    public int size() {
        return items.size();
    }
}