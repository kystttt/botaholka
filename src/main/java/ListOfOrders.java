import java.util.HashMap;

/**
 * Класс для всех текущих заказов
 */
public class ListOfOrders {

    /**
     * Список всех текущих заказов с любым статусом кроме "выдан"(id заказа, заказ)
     */
    private HashMap<Integer, Order> orders;

    /**
     * Переменная отвечающая за нумерацию обновляя отсчёт после каждого запуска
     */
    private int orderId = 1;

    public ListOfOrders() {
        orders = new HashMap<>();
    }

    /**
     * Возвращает конкретный Order по его order_id
     * @param order_id id заказа
     * @return Order
     */
    public Order getValue(int order_id) {
        return orders.get(order_id);
    }

    /**
     * Создание нового Order в ListOrders
     * @param order новый заказ
     */
    public void putOrder(Order order) {
        order.setOrderId(orderId);
        orders.put(orderId,order);
        orderId++;
    }

    /**
     * Возвращает список со всеми заказами
     */
    public HashMap<Integer, Order> getHashMap(){
        return orders;
    }

    /**
     * Удаляет заказ из списка заказов по его id
     */
    public void removeById(Integer order_id){
        orders.remove(order_id);
    }

    /**
     * Обнуляет ListOfOrders
     */
    void clearList(){
        orders = new HashMap<>();
        orderId = 1;
    }
}