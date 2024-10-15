import java.util.HashMap;

public enum ListOfOrders {
    INSTANCE;
    /**
     * Список всех текущих заказов с любым статусом кроме выдан
     */
    private HashMap<Integer, Order> orders;
    /**
     * Переменная отвечающая за нумерацию обновляя отсчёт после каждого запуска
     */
    private int order_id = 1;

    private ListOfOrders() {
        // Initialize configValue here, or load it from a file, database, etc.
        orders = new HashMap<>();
    }


    /**
     * Возвращает конкретный Order по его order_id
     * @param order_id id заказа
     * @return Order
     */
    public Order getValue(Integer order_id) {
        return orders.get(order_id);
    }

    /**
     * Создание нового Order в ListOrders
     * @param order новый заказ
     */
    public void putOrder(Order order) {
        order.setOrderId(order_id);
        orders.put(order_id,order);
        order_id++;
    }


    /**
     * Получение заказа по caht_id
     * @param chat_id
     * @return заказ
     */
    public Order getOrder(Long chat_id) {
        return orders.get(chat_id);
    }

    public HashMap<Integer, Order> getListOfOrders(){
        return getListOfOrders();
    }
}