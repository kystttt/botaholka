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

    public TextHandler(ListOfOrders listOfOrders, MenuList menuList) {
        this.listOfOrders = listOfOrders;
        this.menuList = menuList;
        this.constants = new Constants();
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

        switch (message_text) {
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
                output_message = constants.getConst(Constants.Types.DELETE_OUT_MSG);
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

            case("/order"):
               commandListOfOrders(chat_id);
                menuList.setPrevCommand(message_text);
                break;

            case ("/makeOrder"):
                makeOrder(chat_id);
                menuList.setPrevCommand(message_text);
                break;

            default:
                //Это будет исправлено вместе с задачей на контекст диалога
                //if (message_text.startsWith("/duplicate-")) {
//                    commandDuplicate(message_text);
//                } else if (message_text.startsWith("/cancel-")) {
//                    commandCancelOrder(message_text);
//                } else {
//                    commandWrongTypoWord();
//                }
//                break;
                boolean isInt = false;

                try {
                    Integer.parseInt(message_text);
                    isInt = true;
                } catch (NumberFormatException e) {

                }
                menuList.setPrevCommand(isInt ? menuList.getPrevCommand() : message_text);

                if (Objects.equals(menuList.getPrevCommand(), "/menu")){
                    addToCart(message_text);
                }
                else if (Objects.equals(menuList.getPrevCommand(), "/delete")){
                    deleteFromCart(message_text);
                }
                else{
                    output_message = constants.getConst(Constants.Types.ERROR_TYPE);
                }
                break;

        }
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id
     * @return
     */
    public void makeOrder(Long chat_id){
        Order order = new Order(chat_id);
        if (menuList.getCartSize() == 0){
            output_message = constants.getConst(Constants.Types.CART_EMPTY);
            return;
        }

        for(int i = 0; i < menuList.getCartSize(); i++){
            String[] parts = menuList.getCartValue(i).split("[-. ]+");
            order.addToArr(parts[1] + " " + parts[2]);
        }

        listOfOrders.putOrder(order);
        menuList.getCart().clear();
        output_message = constants.getConst(Constants.Types.MAKED_ORDER);
    }

    /**
     * Повторяет заказ по его id
     */
    private void commandDuplicate(String messageText) {
        String msgNumber = messageText.split("-")[1];

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (msgNumber.equals(Long.toString(
                    listOfOrders.getHashMap().get(key).getChatId()))) {
                listOfOrders.putOrder(listOfOrders.getHashMap().get(key));
                output_message = "Заказ №" + listOfOrders.getHashMap().get(key).getChatId() + " продублирован ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", msgNumber);
    }
    /**
     * Метод, который добавляет по индексу (первому числу) товар в корзину
     * @param dishIndexStr
     */
    public void addToCart(String dishIndexStr) {
        try {
            int dishIndex = Integer.parseInt(dishIndexStr);
            if (menuList.getMenulist().containsKey(dishIndex)) {
                String dishDetails = dishIndexStr + ". " + menuList.getMenulist().get(dishIndex); // Получаем детали блюда
                menuList.addToCart(dishDetails);
                output_message = constants.getConst(Constants.Types.DISH_ADDED) + dishDetails +
                        constants.getConst(Constants.Types.YOUR_CART);
            } else {
                output_message = constants.getConst(Constants.Types.ERROR_UNDEFIND_NUM);
            }
        } catch (NumberFormatException e) {
            output_message = constants.getConst(Constants.Types.ERROR_TYPE);
        }
    }

    /**
     * Метод, который показывает корзину покупателя, список заказа.
     */
    public void viewCart() {
        if (menuList.getCartSize() == 0) {
            output_message = constants.getConst(Constants.Types.CART_EMPTY);
            return;
        }
        StringBuilder cartContents = new StringBuilder(constants.getConst(Constants.Types.YOUR_ORDER));
        ArrayList<String> cartItems = menuList.getCart();
        for (int i = 0; i < cartItems.size(); i++) {
            cartContents.append(i).append(". ").append(cartItems.get(i)).append("\n");
        }

        output_message = cartContents.toString();
    }

    /**
     * Показывает заказ по чат айди
     * @param chat_id
     */


    /** Метод, который удаляет из корзины блюдо
     * @param dishIndexStr
     */
    public void deleteFromCart(String dishIndexStr){

        try {
            int idx = Integer.parseInt(dishIndexStr);
            if (idx >= 0 && idx < menuList.getCartSize()) {
                menuList.removeFromCart(idx); // Удаляем элемент из корзины через метод
                output_message = constants.getConst(Constants.Types.SUCCES_DELETE_DISH) + constants.getConst(Constants.Types.YOUR_CART);
            } else {
                output_message = constants.getConst(Constants.Types.ERROR_INDEX);
            }
        } catch (NumberFormatException e) {
            output_message = constants.getConst(Constants.Types.ERROR_TYPE);
        }
    }

    /**
     * Удаляет заказ по его id
     */
    private void commandCancelOrder(String messageText) {
        String msgNumber = messageText.split("-")[1];

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (msgNumber.equals(Long.toString(
                    listOfOrders.getHashMap().get(key).getChatId()))) {
                Long deletedId = listOfOrders.getHashMap().get(key).getChatId();
                listOfOrders.removeById(listOfOrders.getHashMap().get(key).getOrder_id());
                output_message = "Заказ №" + deletedId + " удалён ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", msgNumber);
    }

    /**
     * Метод, который вызывает меню(показывает, что есть в ассортименте)
     */
    public void menuCalling() {
        try (FileReader file = new FileReader("src/main/resources/menu.json")){
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
            StringBuilder menuBuilder = new StringBuilder(constants.getConst(Constants.Types.MENU));

            Iterator<String> keys = jsonObject.keySet().iterator();
            int index = 1;
            while (keys.hasNext()) {
                String name = keys.next();
                Long cost = (Long) jsonObject.get(name);
                menuList.getMenulist().put(index, name + " - " + cost + " рублей");
                menuBuilder.append(index).append(". ").append(name).append(" - ").append(cost).append(" рублей\n");
                index++;
            }
            menuBuilder.append(constants.getConst(Constants.Types.CHOOSE));
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
            if (chat_id.equals(listOfOrders.getHashMap().get(key).getChatId())) {
                stringBuilder.append(listOfOrders.getHashMap().get(key).formMessageForClient());
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
