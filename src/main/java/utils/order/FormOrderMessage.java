package utils.order;

import menu.Menu;

/**
 * Формирует сообщения для класса заказа
 */
public class FormOrderMessage {

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
        output = String.format("""
                Заказ №%d
                """, order.getId());
        output += formOrderList(order, menu);
        output += order.getStatus() + "\n";
        output += String.format("""
                Итого: %d руб.
                """, order.formSum(menu));
        return output;
    }

    /**
     * Форматирует сообщение дл продавца
     * @param order
     * @return номер заказа и его статус готовности
     */
    public String forSeller(Order order){
        return "Заказ № " + order.getId()  + " " + order.getStatus();

    }

    /**
     * Возвращает сообщение в формате для вывода истории заказов
     */
    public String forHistory(Order order, Menu menu){
        return "Заказ №" + order.getId() + "\n" +
                formOrderList(order, menu) +
                " Сумма заказа: " +
                order.formSum(menu) + "руб.\n";
    }
}
