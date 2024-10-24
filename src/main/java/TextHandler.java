import MenuLogic.Menu;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {

    private ListOfOrders listOfOrders;

    private MenuList menuList;

    private String output_message = "";

    Menu menu;

    public TextHandler(ListOfOrders listOfOrders, MenuList menuList, Menu menu) {
        this.listOfOrders = listOfOrders;
        this.menuList = menuList;
        this.menu = menu;
    }

    //TODO Это конструктор чтобы ничего не поломалось,
    // когда ты будешь мерджить это к себе
    public TextHandler(ListOfOrders listOfOrders, MenuList menuList) {
        this.listOfOrders = listOfOrders;
        this.menuList = menuList;
    }

    /**
     * Команда /start в боте
     */
    private void commandStart() {
        output_message = Constants.START_CONST;
    }

    /**
     * Команда /help в боте
     */
    private void commandHelp() {
        output_message = Constants.HELP_CONST;;
    }

    /**
     * Метод при вызове команды, которой нет у бота
     */
    private void commandWrongTypoWord() {
        output_message = Constants.ERROR_COMMAND;;
    }

    /**
     * геттер для output_message
     *
     * @return возвращает output_message
     */
    public String getOutputMassage(String message_text, Long chat_id) {
        logic(message_text, chat_id);
        return output_message;
    }

    /**
     * Реализует логику бота
     *
     * @param message_text переменная с текстом сообщения пользователя
     */
    private void logic(String message_text, Long chat_id) {
        String[] msg_txt = message_text.split(" ");


        switch (msg_txt[0]) {
            case ("/help"):
                commandHelp();
                menuList.setPrevCommand(message_text);
                break;

            case ("/start"):
                commandStart();
                menuList.setPrevCommand(message_text);
                break;

            case ("/listoforders"):
                commandListOfOrders(chat_id);
                menuList.setPrevCommand(message_text);
                break;

            case ("/delete"):
                output_message = Constants.DELETE_OUT_MSG_CONST;
                menuList.setPrevCommand(message_text);
                break;

            case("/cart"):
                viewCart();
                menuList.setPrevCommand(message_text);

                break;

            case("/menu"):
                menuCalling();
                menuList.setPrevCommand(message_text);
                break;

            case ("/makeOrder"):
                makeOrder(chat_id);
                menuList.setPrevCommand(message_text);
                break;

            case ("/duplicate"):
                commandDuplicate(msg_txt[1]);
                break;

            case ("/cancel"):
                commandCancelOrder(msg_txt[1]);
                break;
            default:

                boolean isInt = false;

                try {
                    Integer.parseInt(message_text);
                    isInt = true;
                } catch (NumberFormatException ignored) {}
                menuList.setPrevCommand(isInt ? menuList.getPrevCommand() : message_text);

                if (Objects.equals(menuList.getPrevCommand(), "/menu")) {
                    addToCart(message_text);
                } else if (Objects.equals(menuList.getPrevCommand(), "/delete")) {
                    deleteFromCart(message_text);
                } else {
                    output_message = Constants.ERROR_TYPE_CONST;
                }

                break;

        }
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id
     * @return
     */
    //TODO Измени с учётом нового класса Menu
    public void makeOrder(Long chat_id){
        Order order = new Order(chat_id);
        if (menuList.getCartSize() == 0){
            output_message = Constants.CART_EMPTY_CONST;
            return;
        }

        for(int i = 0; i < menuList.getCartSize(); i++){
            String[] parts = menuList.getCartValue(i).split("[-. ]+");
            order.addToArr(parts[1] + " " + parts[2]);
        }

        listOfOrders.putOrder(order);
        menuList.getCart().clear();
        output_message = Constants.MAKED_ORDER_CONST;
    }

    /**
     * Повторяет заказ по его id
     */
    private void commandDuplicate(String messageTxtIndex) {

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            Order currentOrder = listOfOrders.getValue(key);

            if (messageTxtIndex.equals(Long.toString(
                    currentOrder.getOrderId()))) {
                listOfOrders.putOrder(new Order(currentOrder));
                output_message = "Заказ №" + currentOrder.getOrderId() + " продублирован ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
    }
    /**
     * Метод, который добавляет по индексу (первому числу) товар в корзину
     * @param dishIndexStr
     */
    //TODO Измени с учётом нового класса Menu
    public void addToCart(String dishIndexStr) {
        try {
            int dishIndex = Integer.parseInt(dishIndexStr);
            if (menuList.getMenulist().containsKey(dishIndex)) {
                String dishDetails = dishIndexStr + ". " + menuList.getMenulist().get(dishIndex); // Получаем детали блюда
                menuList.addToCart(dishDetails);
                output_message = Constants.DISH_ADDED_CONST + dishDetails +
                        Constants.YOUR_CART_CONST;
            } else {
                output_message = Constants.ERROR_UNDEFIND_NUM_CONST;
            }
        } catch (NumberFormatException e) {
            output_message = Constants.ERROR_TYPE_CONST;
        }
    }

    /**
     * Метод, который показывает корзину покупателя, список заказа.
     */
    //TODO Измени с учётом нового класса Menu
    public void viewCart() {
        if (menuList.getCartSize() == 0) {
            output_message = Constants.CART_EMPTY_CONST;
            return;
        }
        StringBuilder cartContents = new StringBuilder(Constants.YOUR_ORDER_CONST);
        ArrayList<String> cartItems = menuList.getCart();
        for (int i = 0; i < cartItems.size(); i++) {
            cartContents.append(i).append(". ").append(cartItems.get(i)).append("\n");
        }

        output_message = cartContents.toString();
    }

    /** Метод, который удаляет из корзины блюдо
     * @param dishIndexStr
     */
    public void deleteFromCart(String dishIndexStr){

        try {
            int idx = Integer.parseInt(dishIndexStr);
            if (idx >= 0 && idx < menuList.getCartSize()) {
                menuList.removeFromCart(idx); // Удаляем элемент из корзины через метод
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

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            Order currentOrder = listOfOrders.getValue(key);

            if (messageTxtIndex.equals(String.valueOf(
                    currentOrder.getOrderId()))) {
                listOfOrders.removeById(currentOrder.getOrderId());
                output_message = "Заказ №" + messageTxtIndex + " удалён ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
    }

    /**
     * Метод, который вызывает меню(показывает, что есть в ассортименте)
     */
    //TODO Измени с учётом нового класса Menu
    public void menuCalling() {
        try (FileReader file = new FileReader("src/main/resources/menu.json")){
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            StringBuilder menuBuilder = new StringBuilder(Constants.MENU_CONST);

            Iterator<String> keys = jsonObject.keySet().iterator();
            int index = 1;
            while (keys.hasNext()) {
                String name = keys.next();
                Long cost = (Long) jsonObject.get(name);
                menuList.getMenulist().put(index, name + " - " + cost + " рублей");
                menuBuilder.append(index).append(". ").append(name).append(" - ").append(cost).append(" рублей\n");
                index++;
            }
            menuBuilder.append(Constants.CHOOSE_CONST);
            output_message = menuBuilder.toString();


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Метод для показа текущих заказов клиента по его chat_id
     *
     * @param chat_id номер чата
     */
    private void commandListOfOrders(Long chat_id) {
        boolean atLeastOnce = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (chat_id.equals(listOfOrders.getValue(key).getChatId())) {
                stringBuilder.append(listOfOrders.getValue(key).formMessageForClient(menu));
                stringBuilder.append("\n");
                atLeastOnce = true;
            }
        }

        output_message = "Ваши заказы:\n";
        output_message += stringBuilder.toString();
        output_message += Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;;
        if (!atLeastOnce) {
            output_message = "У вас нету действительных заказов";
        }
    }
}
