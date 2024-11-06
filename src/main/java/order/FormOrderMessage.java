package order;

import menu.Menu;

/**
 * Формирует сообщения для класса заказа
 */
public class FormOrderMessage {

    private String formOrderList(Order order, Menu<String, Integer> menu){
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : order.getItems()){
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
    public String forClient(Order order, Menu<String, Integer> menu) {
        String output;
        output = String.format("""
                Заказ №%d
                """, order.getId());
        output += formOrderList(order, menu);
        output += String.format("""
                Итого: %d руб.
                """, order.formSum(menu));
        return output;
    }


}
