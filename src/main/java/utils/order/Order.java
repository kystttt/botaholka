package utils.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import menu.Menu;

/**
 * Класс заказа
 */
public class Order {

    private int sum;

    private int id;

    private final Long chatId;
    private int currentStatusListIndex = 1;

    public Order(Order order){
        this.chatId = order.chatId;
        this.id = order.id;
        this.items = order.items;
    }

    public Order(int sum, int id, Long chatId, List<String> items) {
        this.sum = sum;
        this.id = id;
        this.chatId = chatId;
        this.items = items;
    }

    /**
     * Список возможных статусов заказа
     */
    private final List<String> statusList = new ArrayList<>(
            List.of("Не принят", "готовится", "Приготовлен", "Выдан"));
    private String status = statusList.getFirst();
    /**
     *Список того что заказал клиент
     */
    private List<String> items = new ArrayList<>();

    public Order(Long chat_id){
        this.chatId = chat_id;
    }

    public int getId(){
        return id;
    }

    public Long getChatId(){
        return chatId;
    }

    public void setOrderId(int order_id){
        this.id = order_id;
    }

    public List<String> getItems(){return items;}

    public String itemsToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String str : items){
            stringBuilder.append(str)
                    .append("/");
        }
        return stringBuilder.toString();
    }

    public String getStatus(){return status;}

    public int setStatus(){
        this.status = statusList.get(currentStatusListIndex++);
        if (this.status.equals("Выдан")){
            return 1;
        }
        return  0;
    }

    /**
     * Добавляет цену за отдельный продукт в общую сумму
     * @param str название лота в меню, которому соответствует некоторая цена в menu.json
     */
    public void addToArr(String str) {
        items.add(str);
    }

    /**
     * Функция считающая сумму заказа исходя из того что заказал человек
     */
    public int formSum(Menu menu) {
        int sum = 0;
        for(String s: items){
            sum += menu.getCost(s);
        }
        this.sum = sum;
        return sum;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(chatId, order.chatId) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, status, items);
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", chatId=" + chatId +
                ", id=" + id +
                '}';
    }
}