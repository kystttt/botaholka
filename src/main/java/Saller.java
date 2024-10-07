import java.util.HashMap;

/**
 * Класс продавца
 */
public class Saller {
    ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
    /**
     * Хранится список заказов для продавца
     */
    HashMap<Long, Order> listOfOrdersInSaller = listOfOrders.getListOfOrders();

    /**
     * Хранятся статусы заказов, их можно вынуть по orderId
     */
    HashMap<Long, String> listOfOrderStatus = new HashMap<>();

    public Saller(){
        for (Long orderId : listOfOrdersInSaller.keySet()){
            listOfOrderStatus.put(orderId, listOfOrdersInSaller.get(orderId).getStatus(orderId));
        }
    }


}
