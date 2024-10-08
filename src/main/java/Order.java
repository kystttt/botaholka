import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Order {
    /**
     * Список возможных состояний статуса заказа
     */
    private final ArrayList<String> statusState = new ArrayList<>(List.of(
            "Не принят",
            "Готовится",
            "Готов",
            "Выдан"));


    private int order_id;
    private final Long chat_id;
    /**
     *Список того что заказа клиент
     */
    private ArrayList<String> orderList = new ArrayList<>();
    public int sum;
    /**
     * Статус заказа
     */
    private String status = "Не принят";
    /**
     * Время к которму надо приготовить заказ
     */
    private final String timeTo = "К ближайшему";


    public Order(Long chat_id){
        this.chat_id = chat_id;

    }

    public int getOrder_id(){
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
     * Функция считающая сумм заказа изходя из того что заказал человек
     */
    public void formSum() {
        sum = 0;
        try {
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(new FileReader("src/main/resources/menu.json"));
            for (String s : orderList) {
                sum += (int) (long) jsonObject.get(s);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Функция формирующая текстовое представления Order
     * @return текстовое представления Order
     */
    public String formMessageForClient() {
        String output;
        formSum();
        output = String.format("""
                Заказ №%d
                """, order_id);
        output += String.join("\n", orderList);
        output += String.format("""
                \nИтого: %d руб.
                """, sum);
        return output;
    }

}
