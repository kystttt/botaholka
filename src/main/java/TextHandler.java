import menu.*;
import order.FormMessage;
import order.ListOfOrders;
import order.Order;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {

    private final ListOfOrders listOfOrders;

    private final Cart cart;


    private String output_message = "";

    private String prevCommand = "";

    Menu<String, Integer> menu;

    // Метод для установки предыдущей команды
    public void setPrevCommand(String command) {
        this.prevCommand = command;
    }

    // Метод для получения предыдущей команды
    public String getPrevCommand() {
        return this.prevCommand;
    }

    public TextHandler(ListOfOrders listOfOrders, Cart cart, Menu menu) {
        this.listOfOrders = listOfOrders;
        this.cart = cart;
        this.menu = menu;
    }

    /**
     * Обрабатывает сообщение
     *
     * @param message_text Текст сообщения пользователя
     */
    public String processMessage(String message_text, Long chat_id) {
        String[] msg_txt = message_text.split(" ");

        switch (msg_txt[0]) {
            case ("/help"):
                return Constants.HELP_CONST;

            case ("/start"):
                return Constants.START_CONST;

            case ("/listoforders"):
                listOfOrders(chat_id);
                break;

            case ("/delete"):
                setPrevCommand(msg_txt[0]);
                return Constants.DELETE_OUT_MSG_CONST;

            case("/cart"):
                viewCart();
                break;

            case("/menu"):
                menuCalling();
                setPrevCommand(msg_txt[0]);
                break;

            case ("/order"):
                makeOrder(chat_id);
                break;

            case ("/duplicate"):
                commandDuplicate(msg_txt[1]);
                break;

            case ("/cancel"):
                commandCancelOrder(msg_txt[1]);
                break;

            default:
                if (Objects.equals(getPrevCommand(), "/menu")) {
                    addToCart(message_text);
                } else if (Objects.equals(getPrevCommand(), "/delete")) {
                    deleteFromCart(message_text);
                } else {
                    output_message = Constants.ERROR_COMMAND;
                }

                break;
        }
        return output_message;
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id Id того пользователя для кого создается заказ
     */
    public void makeOrder(Long chat_id){
        Order order = new Order(chat_id);
        if (cart.getCartSize() == 0){
            output_message = Constants.CART_EMPTY_CONST;
            return;
        }

        for(int i = 0; i < cart.getCartSize(); i++){
            String[] parts = cart.getCartValue(i).split("[-. ]+");
            order.addToArr(parts[0]);
        }

        listOfOrders.putOrder(order);
        cart.cartClear();
        output_message = Constants.MAKED_ORDER_CONST;
    }

    /**
     * Повторяет заказ по его id
     */
    private void commandDuplicate(String messageTxtIndex) {

        for (Order order : listOfOrders.values()) {
            if (messageTxtIndex.equals(Long.toString(
                    order.getId()))) {
                listOfOrders.putOrder(new Order(order));
                output_message = "Заказ №" + order.getId() + " продублирован ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
    }

    /**
     * Метод, который добавляет по названию товар в корзину
     */
    public void addToCart(String dishName) {
        if (menu.getCost(dishName) != -1) {
            String dishDetails =  dishName + " - " + menu.getCost(dishName) + " рублей"; // Получаем детали блюда
            cart.addToCart(dishDetails);
            output_message = Constants.DISH_ADDED_CONST + dishDetails +
                    Constants.YOUR_CART_CONST;
        } else {
            output_message = Constants.ERROR_UNDEFIND_NUM_CONST;
        }
    }

    /**
     * Метод, который показывает корзину покупателя.
     */
    public void viewCart() {
        if (cart.getCartSize() == 0) {
            output_message = Constants.CART_EMPTY_CONST;
            return;
        }
        StringBuilder cartContents = new StringBuilder(Constants.YOUR_ORDER_CONST);
        ArrayList<String> cartItems = cart.getCart();
        for (int i = 0; i < cartItems.size(); i++) {
            cartContents.append(i+1).append(". ").append(cartItems.get(i)).append("\n");
        }

        output_message = cartContents.toString();
    }

    /** Метод, который удаляет из корзины блюдо по индексу из корзины
     */
    public void deleteFromCart(String dishIndexStr){

        try {
            int idx = Integer.parseInt(dishIndexStr) - 1;
            if (idx >= 0 && idx < cart.getCartSize()) {
                cart.removeFromCart(idx);
                output_message = Constants.SUCCES_DELETE_DISH_CONST + Constants.YOUR_CART_CONST;
            } else {
                output_message = Constants.ERROR_INDEX_CONST;
            }
        } catch (NumberFormatException e) {
            output_message = Constants.ERROR_TYPE_CONST;
        }
    }

    /**
     * Удаляет заказ по его id
     */
    private void commandCancelOrder(String messageTxtIndex) {

        for (Order order : listOfOrders.values()) {
            if (messageTxtIndex.equals(String.valueOf(
                    order.getId()))) {
                listOfOrders.remove(order.getId());
                output_message = "Заказ №" + messageTxtIndex + " удалён ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
    }

    /**
     * Метод, который вызывает меню(показывает, что есть в ассортименте)
     */

    public void menuCalling() {
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
    }


    /**
     * Метод для показа текущих заказов клиента по его chat_id
     *
     * @param chat_id номер чата
     */
    private void listOfOrders(Long chat_id) {
        boolean atLeastOnce = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer key : listOfOrders.keySet()) {
            if (chat_id.equals(listOfOrders.get(key).getChatId())) {
                stringBuilder.append(new FormMessage().forClient(listOfOrders.get(key), menu));
                stringBuilder.append("\n");
                atLeastOnce = true;
            }
        }

        output_message = "Ваши заказы:\n";
        output_message += stringBuilder.toString();
        output_message += Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;
        if (!atLeastOnce) {
            output_message = "У вас нету активных заказов";
        }
    }
}
