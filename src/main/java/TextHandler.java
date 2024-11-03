import menu.*;
import order.FormOrderMessage;
import storages.Cart;
import storages.ListOfOrders;
import order.Order;
import utils.Constants;

import java.util.Objects;

/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {

    private final ListOfOrders listOfOrders;

    private final Cart cart;

    Menu menu;

    public TextHandler(ListOfOrders listOfOrders, Cart cart, Menu menu) {
        this.listOfOrders = listOfOrders;
        this.cart = cart;
        this.menu = menu;
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id Id того пользователя для кого создается заказ
     */
    public String makeOrder(Long chat_id){
        Order order = new Order(chat_id);
        if (cart.isEmpty()){
            return Constants.CART_EMPTY_CONST;
        }

        for (String s : cart) {
            String[] parts = s.split("[-. ]+");
            order.addToArr(parts[0]);
        }

        listOfOrders.putOrder(order);
        cart.clear();
        return Constants.MADE_ORDER_CONST;
    }

    /**
     * Повторяет заказ по его id
     */
    public String duplicateOrder(String messageTxtIndex) {
        String output_message;
        for (Order order : listOfOrders.values()) {
            if (messageTxtIndex.equals(Long.toString(
                    order.getId()))) {
                listOfOrders.putOrder(new Order(order));
                output_message = "Заказ №" + order.getId() + " продублирован ";
                return output_message;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
        return output_message;
    }

    /**
     * Метод, который добавляет по названию товар в корзину
     */
    public String addToCart(String dishName) {
        if (menu.getCost(dishName) != -1) {
            String dishDetails =  dishName + " - " + menu.getCost(dishName) + " рублей";
            cart.add(dishDetails);
            return Constants.DISH_ADDED_CONST + dishDetails +
                    Constants.YOUR_CART_CONST;
        } else {
            return Constants.ERROR_UNDEFINED_NUMB_CONST;
        }
    }

    /**
     * Метод, который показывает корзину покупателя.
     */
    public String viewCart() {
        if (cart.isEmpty()) {
            return Constants.CART_EMPTY_CONST;
        }

        StringBuilder cartContents = new StringBuilder(Constants.YOUR_ORDER_CONST);

        for (int i = 0; i < cart.size(); i++) {
            cartContents.append(i+1).append(". ").append(cart.get(i)).append("\n");
        }
        return cartContents.toString();
    }

    /**
     * Метод, который удаляет из корзины блюдо по индексу из корзины
     */
    public String deleteFromCart(String dishIndexStr){
        try {
            int idx = Integer.parseInt(dishIndexStr);
            if (idx - 1 >= 0 && idx < cart.size()) {
                cart.remove(idx);
                return Constants.SUCCESS_DELETE_DISH_CONST + Constants.YOUR_CART_CONST;
            } else {
                return Constants.ERROR_INDEX_CONST;
            }
        } catch (NumberFormatException e) {
            return Constants.ERROR_TYPE_CONST;
        }
    }

    /**
     * Удаляет заказ по его id
     */
    public String cancelOrder(String index, Long chatId) {
        int idx;
        try{
            idx = Integer.parseInt(index);
        }
        catch (NumberFormatException e){
            return Constants.ERROR_TYPE_CONST;
        }
        for (Order order : listOfOrders.values()) {
            if (
                    idx == order.getId() &&
                            Objects.equals(order.getChatId(), chatId)
            ) {
                listOfOrders.remove(order.getId());
                return "Заказ №" + index + " удалён ";
            }
        }
        return Constants.ERROR_INDEX_CONST;
    }

    /**
     * Метод, который вызывает меню(показывает, что есть в ассортименте)
     */

    public String menuCalling() {
        String output_message;
        StringBuilder menuBuilder = new StringBuilder(Constants.MENU_CONST);
        int index = 1;
        for (String name: menu.getFoodNames()){
            String stringCost = menu.getCost(name).toString();
            menuBuilder
                    .append(index)
                    .append(". ")
                    .append(name)
                    .append(" - ")
                    .append(stringCost)
                    .append(" рублей\n");
            index++;
        }
        menuBuilder.append(Constants.CHOOSE_CONST);
        output_message = menuBuilder.toString();
        return output_message;
    }


    /**
     * Метод для показа текущих заказов клиента по его chat_id
     *
     * @param chat_id номер чата
     */
    public String listOfOrders(Long chat_id) {
        String output_message;
        boolean atLeastOnce = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer key : listOfOrders.keySet()) {
            if (chat_id.equals(listOfOrders.get(key).getChatId())) {
                stringBuilder.append(new FormOrderMessage().forClient(listOfOrders.get(key), menu));
                stringBuilder.append("\n");
                atLeastOnce = true;
            }
        }

        output_message = "Ваши заказы:\n";
        output_message += stringBuilder.toString();
        output_message += Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;
        if (!atLeastOnce) {
            output_message = Constants.NO_AVAILABLE_ORDERS;
        }
        return output_message;
    }
}
