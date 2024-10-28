import java.util.ArrayList;

import menu.MenuImpl;

/**
 * Класс заказа
 */
public class Order {
//    /**
//     *Возможные состояния статуса заказа
//     */
//    private enum StatusState{
//        Not_Accepted,
//        Preparing,
//        Ready,
//        Given_Out;
//    }
//
    public Order(Order order){
        this.chat_id = order.chat_id;
        this.order_id = order.order_id;
        this.orderList = order.orderList;
    }

    private int order_id;

    private final Long chat_id;

    /**
     *Список того что заказа клиент
     */
    private ArrayList<String> orderList = new ArrayList<>();

    public int sum;

//    /**
//     * Статус заказа
//     */
//    private StatusState statusState;

//    /**
//     * Время к которому надо приготовить заказ
//     */
//    private final String timeTo = "К ближайшему";

    public Order(Long chat_id){
        this.chat_id = chat_id;
    }

    public int getOrderId(){
        return order_id;
    }

    public Long getChatId(){
        return chat_id;
    }

    public void setOrderId(int order_id){
        this.order_id = order_id;
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
    private void formSum(MenuImpl menu) {
        sum = 0;
        for(String s: orderList){
            sum += menu.getCost(s);
        }
    }


    private String formOrderList(MenuImpl menu){
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
    public String formMessageForClient(MenuImpl menu) {
        String output;
        formSum(menu);
        output = String.format("""
                Заказ №%d
                """, order_id);
        output += formOrderList(menu);
        output += String.format("""
                Итого: %d руб.
                """, sum);
        return output;
    }
}