package order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import menu.Menu;

/**
 * Класс заказа
 */
public class Order {

    public Order(Order order){
        this.chatId = order.chatId;
        this.id = order.id;
        this.items = order.items;
        this.status = order.status;
        this.currentStatusListIndex = order.currentStatusListIndex;
    }

    private int id;

    private final Long chatId;
    private int currentStatusListIndex = 1;

    /**
     * Список возможных статусов заказа
     */
    private final List<String> statusList = new ArrayList<>(
            List.of("Не принят", "готовится", "Приготовлен", "Выдан"));
    private String status = statusList.getFirst();
    /**
     *Список того что заказал клиент
     */
    private ArrayList<String> items = new ArrayList<>();

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

    public ArrayList<String> getItems(){return items;}
    public String getStatus(){return status;}

    public void setStatus(){
        if (!this.status.equals("Выдан")){
            this.status = statusList.get(currentStatusListIndex++);
        }

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
        return sum;
    }
}