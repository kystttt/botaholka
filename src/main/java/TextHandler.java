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

    final String START_CONST = """
                                Добро пожаловать в бота
                                """;
    final String ERROR_BRANCH = """
            В качестве кого вы бы хотели продолжить?
            /seller - продавец
            /buyer - покупатель
            """;
    final String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветсвие пользователя
            /menu - меню для выбора блюда
            /cart - посмотреть корзину
            /delete - удалить товар из корзины
            /listoforders - просмотр текущих заказов
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;
              
    private final String ECHO_CONST = "Вы ввели: ";
    private String output_message = "";
    private final String MENU_CONST = "Меню: \n";
    private final String CHOOSE_CONST = "Введите номер того, что хотите заказать: ";
    private final String ERROR_TYPE_CONST = "Ошибка: индекс блюда должен быть числом.";
    private final String DISH_ADDED_CONST = "Блюдо добавлено в корзину:\n";
    private final String ERROR_UNDEFIND_NUM_CONST = "Ошибка: такого блюда нет в меню.";
    private final String SUCCES_DELETE_DISH_CONST = "Блюдо успешно удалено ";
    private final String CART_EMPTY_CONST = "Корзина пуста.";
    private final String YOUR_ORDER_CONST = "Ваш заказ:\n";
    private final String DELETE_OUT_MSG_CONST = "Введите номер блюда, которое хотите удалить: ";
    private final String FUNCS_FOR_LIST_OF_ORDERS_BUYER = """
            Ваши функции:
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;

    MenuList mnlst = MenuList.INSTANCE;

    /**
     * Метод, который вызывает меню, в котором показывается
     * порядковый номер блюда в меню, название блюда, а также его стоимость
     */
    public void menuCalling() {
        MenuList mnlst = MenuList.INSTANCE;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/main/resources/menu.json"));
            StringBuilder menuBuilder = new StringBuilder(MENU_CONST);

            Iterator<String> keys = jsonObject.keySet().iterator();
            int index = 1;
            while (keys.hasNext()) {
                String name = keys.next();
                Long cost = (Long) jsonObject.get(name);
                mnlst.getMenulist().put(index, name + " - " + cost + " рублей");
                menuBuilder.append(index).append(". ").append(name).append(" - ").append(cost).append(" рублей\n");
                index++;
            }
            menuBuilder.append(CHOOSE_CONST);
            output_message = menuBuilder.toString();


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Метод, который добавляет по индексу (первому числу) товар в корзину
     * @param dishIndexStr
     */
    public void addToCart(String dishIndexStr) {
        try {
            int dishIndex = Integer.parseInt(dishIndexStr);
            if (mnlst.getMenulist().containsKey(dishIndex)) {
                String dishDetails = dishIndexStr + ". " + mnlst.getMenulist().get(dishIndex); // Получаем детали блюда
                mnlst.addToCart(dishDetails);
                output_message = DISH_ADDED_CONST + dishDetails;
            } else {
                output_message = ERROR_UNDEFIND_NUM_CONST;
            }
        } catch (NumberFormatException e) {
            output_message = ERROR_TYPE_CONST;
        }
    }

    /**
     * Метод, который показывает корзину покупателя, список заказа.
     */
    public void viewCart() {
        if (mnlst.getCartSize() == 0) {
            output_message = CART_EMPTY_CONST;
            return;
        }
        StringBuilder cartContents = new StringBuilder(YOUR_ORDER_CONST);
        ArrayList<String> cartItems = mnlst.getCart();
        for (int i = 0; i < cartItems.size(); i++) {
            cartContents.append(i).append(". ").append(cartItems.get(i)).append("\n");
        }

        output_message = cartContents.toString();
    }

    /**
     * Метод, который удаляет из корзины блюдо
     * @param dishIndexStr
     */
    public void deleteFromCart(String dishIndexStr){

        try {
            int idx = Integer.parseInt(dishIndexStr);
            if (idx >= 0 && idx < mnlst.getCartSize()) {
                mnlst.removeFromCart(idx); // Удаляем элемент из корзины через метод
                output_message = SUCCES_DELETE_DISH_CONST;
            } else {
                output_message = ERROR_UNDEFIND_NUM_CONST;
            }
        } catch (NumberFormatException e) {
            output_message = ERROR_TYPE_CONST;
        }
    }

    private void commandEcho(String str){
        output_message = ECHO_CONST + str;
    }


    /**
     * Команда /start в боте
     */
    private void commandStart(){
        output_message = START_CONST;
    }

    /**
     * Команда /help в боте
     */
     private void commandHelp(){
        output_message = HELP_CONST;
     }

    private void commandWrongTypoWord(){
        output_message = "Введите корректную команду, для списка всех команд - /help";
    }

     public void commandListOfOrders(Long chat_id){
         output_message = "Ваши заказы:\n";
         int i = 1;
         ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
         for(Integer key : listOfOrders.getHashMap().keySet()){
             if(Objects.equals(listOfOrders.getHashMap().get(key).getChatId(), chat_id)){
                 output_message += listOfOrders.getHashMap().get(key).formMessageForClient();
                 output_message += "\n";
             }
         }
         output_message += FUNCS_FOR_LIST_OF_ORDERS_BUYER;
     }

    /**
     * геттер для output_message
     * @return возвращает output_message
     */
    public String getOutputMassage(){
         return output_message;
     }
    
    /**
     * Удаляет заказ по его id
     */
    private void commandDuplicate(String messageText) {
        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        for(Integer key : listOfOrders.getHashMap().keySet()) {
            if(messageText.endsWith( Long.toString(listOfOrders.getHashMap().get(key).getChatId()))){
                listOfOrders.putOrder(listOfOrders.getHashMap().get(key));
                output_message = "Заказ №" + listOfOrders.getHashMap().get(key).getChatId() + " продублирован ";
                break;
            }
        }

    }
  
      /**
     * Реализует логику бота
     * @param message_text переменная с текстом сообщения пользователя
     */
     public void logic(String message_text, Long chat_id) throws IOException, ParseException {
         switch (message_text) {
             case ("/help"):
                 commandHelp();
                 mnlst.setPrevCommand(message_text);
                 break;

             case ("/start"):
                 commandStart();
                 mnlst.setPrevCommand(message_text);
                 break;

             case("/order"):
                 commandOrder(chat_id);
                 mnlst.setPrevCommand(message_text);
                 break;

             case("/menu"):
                 menuCalling();
                 mnlst.setPrevCommand(message_text);
                 break;

             case("/delete"):
                 output_message = DELETE_OUT_MSG_CONST;
                 mnlst.setPrevCommand(message_text);
                 break;
            
             case("/listoforders"):
                commandListOfOrders(chat_id);
                break;
             
             case("/cart"):
                 viewCart();
                 mnlst.setPrevCommand(message_text);
                 break;

             default:
                 boolean isInt = false;

                 try {
                     Integer.parseInt(message_text);
                     isInt = true;
                 } catch (NumberFormatException e) {

                 }
                 mnlst.setPrevCommand(isInt ? mnlst.getPrevCommand() : message_text);

                 if (Objects.equals(mnlst.getPrevCommand(), "/menu")){
                     addToCart(message_text);
                 }
                 else if (Objects.equals(mnlst.getPrevCommand(), "/delete")){
                     deleteFromCart(message_text);
                 }
                 else if (message_text.startsWith("/duplicate-")) {
                    commandDuplicate(message_text);
                }
                else if(message_text.startsWith("/delete-")){
                    commandDeleteOrder(message_text);
                }
                 else{
                     output_message = ERROR_TYPE_CONST;
                 }
                 break;

         }
     }


    /**
     * Повторяет заказ по его id
     */
    private void commandDeleteOrder(String messageText) {
        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        for(Integer key : listOfOrders.getHashMap().keySet()) {
            if(messageText.endsWith( Long.toString(listOfOrders.getHashMap().get(key).getChatId()))){
                listOfOrders.removeById(listOfOrders.getHashMap().get(key).getOrder_id());
                output_message = "Заказ №" + listOfOrders.getHashMap().get(key).getChatId() + " удалён ";
                break;
            }
        }

    }
}
