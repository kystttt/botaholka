package fsm.cfg;

import menu.*;
import order.FormOrderMessage;
import storages.Cart;
import storages.ListOfOrders;
import order.Order;
import utils.Constants;

import java.util.ArrayList;

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
        if (cart.getCartSize() == 0){
            return Constants.CART_EMPTY_CONST;
        }

        for(int i = 0; i < cart.getCartSize(); i++){
            String[] parts = cart.getCartValue(i).split("[-. ]+");
            order.addToArr(parts[0]);
        }

        listOfOrders.putOrder(order);
        cart.cartClear();
        return Constants.MAKED_ORDER_CONST;
    }

    /**
     * Повторяет заказ по его id
     */
    public String commandDuplicate(String messageTxtIndex) {
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
        String output_message;
        if (menu.getCost(dishName) != -1) {
            String dishDetails =  dishName + " - " + menu.getCost(dishName) + " рублей"; // Получаем детали блюда
            cart.addToCart(dishDetails);
            output_message = Constants.DISH_ADDED_CONST + dishDetails +
                    Constants.YOUR_CART_CONST;
        } else {
            output_message = Constants.ERROR_UNDEFINED_NUMB_CONST;
        }
        return output_message;
    }

    /**
     * Метод, который показывает корзину покупателя.
     */
    public String viewCart() {
        String output_message;
        if (cart.getCartSize() == 0) {
            output_message = Constants.CART_EMPTY_CONST;
            return output_message;
        }
        StringBuilder cartContents = new StringBuilder(Constants.YOUR_ORDER_CONST);
        ArrayList<String> cartItems = cart.getCart();
        for (int i = 0; i < cartItems.size(); i++) {
            cartContents.append(i+1).append(". ").append(cartItems.get(i)).append("\n");
        }

        output_message = cartContents.toString();
        return output_message;
    }

    /**
     * Метод, который удаляет из корзины блюдо по индексу из корзины
     */
    public String deleteFromCart(String dishIndexStr){
        try {
            int idx = Integer.parseInt(dishIndexStr) - 1;
            if (idx >= 0 && idx < cart.getCartSize()) {
                cart.removeFromCart(idx);
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
    public String cancelOrder(String messageTxtIndex) {
        String output_message;
        for (Order order : listOfOrders.values()) {
            if (messageTxtIndex.equals(String.valueOf(
                    order.getId()))) {
                listOfOrders.remove(order.getId());
                output_message = "Заказ №" + messageTxtIndex + " удалён ";
                return output_message;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
        return output_message;
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
