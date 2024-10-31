import java.util.ArrayList;

import menu.Menu;

/**
 * Класс заказа
 */
public class Order {

    public Order(Order order){
        this.chatId = order.chatId;
        this.id = order.id;
        this.orderList = order.orderList;
        this.sum = sum;
    }

    private int id;

    private final Long chatId;

    /**
     *Список того что заказа клиент
     */
    private ArrayList<String> orderList = new ArrayList<>();

    public int sum;

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

    /**
     * Функция добавления цены за отдельный продукт в общую сумму
     * @param str название лота в меню, которому соответствует некоторая цена в menu.json
     */
    public void addToArr(String str) {
        orderList.add(str);
    }

    /**
     * Функция считающая сумм заказа исходя из того что заказал человек
     */
    private void formSum(Menu menu) {
        sum = 0;
        for(String s: orderList){
            sum += (int)menu.getCost(s);
        }
    }


    private String formOrderList(Menu menu){
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : orderList){
            stringBuilder
                    .append(s)
                    .append(" - ")
                    .append(menu.getCost(s))
                    .append(" руб.")
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Функция формирующая текстовое представления Order
     * @return текстовое представления Order
     */
    public String formMessageForClient(Menu menu) {
        String output;
        formSum(menu);
        output = String.format("""
                Заказ №%d
                """, id);
        output += formOrderList(menu);
        output += String.format("""
                Итого: %d руб.
                """, sum);
        return output;
    }
}