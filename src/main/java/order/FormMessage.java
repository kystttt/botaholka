package order;

import menu.Menu;

/**
 * Формирует сообщения
 */
public class FormMessage {

    private String formOrderList(Order order, Menu menu){
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
    public String forClient(Order order, Menu menu) {
        String output;
        order.formSum(menu);
        output = String.format("""
                Заказ №%d
                """, order.getId());
        output += formOrderList(order, menu);
        output += String.format("""
                Итого: %d руб.
                """, order.getSum());
        return output;
    }


}
