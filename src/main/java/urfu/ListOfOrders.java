package urfu;

import java.util.HashMap;

/**
 * Синглтон для всех текущих заказов
 */
public enum ListOfOrders {
    INSTANCE;
    /**
     * Список всех текущих заказов с любым статусом кроме выдан(id заказа, заказ)
     */
    private HashMap<Integer, Order> orders;
    /**
     * Переменная отвечающая за нумерацию обновляя отсчёт после каждого запуска
     */
    private int order_id = 1;

    private ListOfOrders() {
        orders = new HashMap<>();
    }

    /**
     * Возвращает конкретный urfu.Order по его order_id
     * @param order_id id заказа
     * @return urfu.Order
     */
    public Order getValue(int order_id) {
        return orders.get(order_id);
    }

    /**
     * Создание нового urfu.Order в ListOrders
     * @param order новый заказ
     */
    public void putOrder(Order order) {
        order.setOrderId(order_id);
        orders.put(order_id,order);
        order_id++;
    }

    /**
     * Возвращает хэшмап со всеми заказами
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
    public void clearList(){
        orders = new HashMap<Integer, Order>();
        order_id = 1;
    }
}