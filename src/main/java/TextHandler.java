import menu.*;
import order.FormOrderMessage;
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

    private String prevCommand = "";

    Menu menu;

    // Метод для установки предыдущей команды
    private void setPrevCommand(String command) {
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

        return switch (msg_txt[0]) {
            case ("/help") -> Constants.HELP_CONST;
            case ("/start") -> Constants.START_CONST;
            case ("/listoforders") -> listOfOrders(chat_id);
            case ("/delete") -> {
                setPrevCommand(msg_txt[0]);
                yield Constants.DELETE_OUT_MSG_CONST;
            }
            case ("/cart") -> {
                setPrevCommand(msg_txt[0]);
                yield viewCart();
            }
            case ("/menu") -> {
                setPrevCommand(msg_txt[0]);
                yield menuCalling();
            }
            case ("/order") -> makeOrder(chat_id);
            case ("/duplicate") -> commandDuplicate(msg_txt[1]);
            case ("/cancel") -> cancelOrder(msg_txt[1]);
            default -> {
                if (Objects.equals(getPrevCommand(), "/menu")) {
                    yield addToCart(message_text);
                } else if (Objects.equals(getPrevCommand(), "/delete")) {
                    yield deleteFromCart(message_text);
                } else {
                    yield Constants.ERROR_COMMAND;
                }
            }
        };
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id Id того пользователя для кого создается заказ
     */
    private String makeOrder(Long chat_id){
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
    private String commandDuplicate(String messageTxtIndex) {
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
    private String addToCart(String dishName) {
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
    private String viewCart() {
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
    private String deleteFromCart(String dishIndexStr){
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
    private String cancelOrder(String messageTxtIndex) {
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

    private String menuCalling() {
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
    private String listOfOrders(Long chat_id) {
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
