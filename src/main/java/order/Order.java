package order;

import java.util.ArrayList;

import menu.Menu;

/**
 * Класс заказа
 */
public class Order {

    public Order(Order order){
        this.chatId = order.chatId;
        this.id = order.id;
        this.items = order.items;
        this.sum = sum;
    }

    private int id;

    private final Long chatId;

    /**
     *Список того что заказал клиент
     */
    private ArrayList<String> items = new ArrayList<>();

    private int sum;

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

    public int getSum(){return sum;}

    public ArrayList<String> getItems(){return items;}

    /**
     * Функция добавления цены за отдельный продукт в общую сумму
     * @param str название лота в меню, которому соответствует некоторая цена в menu.json
     */
    public void addToArr(String str) {
        items.add(str);
    }

    /**
     * Функция считающая сумм заказа исходя из того что заказал человек
     */
    public void formSum(Menu menu) {
        sum = 0;
        for(String s: items){
            sum += (int)menu.getCost(s);
        }
    }


}