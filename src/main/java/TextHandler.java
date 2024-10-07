import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.print.DocFlavor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {
    private final String START_CONST = """
                                Добро пожаловать в бот
                                """;
    private final String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /strt - приветсвие пользователя
            """;
    private final String ECHO_CONST = "Вы ввели: ";
    private final String MENU_CONST = "Меню: ";
    private final String CHOOSE_CONST = "Выберете номер того что хотите заказать ";
    private String output_message;
    HashMap<Integer, String> menuList = new HashMap<>();
    /**
     * Метод, который вызывает меню, в котором показывается
     * порядковый номер блюда в меню, название блюда, а также его стоимость
     */
    public void menuCalling() {

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader("src/main/resources/menu.json"));
            StringBuilder menuBuilder = new StringBuilder(MENU_CONST);

            Iterator<String> keys = jsonObject.keySet().iterator();
            int index = 1;
            while (keys.hasNext()){
                String name = keys.next();
                Long cost = (Long) jsonObject.get(name);
                menuList.put(index, name + " - " + cost + " рублей");
                menuBuilder.append(index).append(". ").append(name).append(" - ").append(cost).append("рублей\n");
                index++;
            }
            menuBuilder.append(CHOOSE_CONST);
            output_message = menuBuilder.toString();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCart(String message){
        int indx = Integer.parseInt(message);
        HashMap<Integer, String> cart = new HashMap<>();
        try{
            if (menuList.containsKey(indx)){
                String menuListValue = menuList.get(indx);
                cart.put(indx, menuListValue);
                output_message = "Текущая корзина:\n" + cart;
            }
            else {
                output_message = "Ошибка: блюда с таким номером не существует";
            }
        } catch (NumberFormatException e){
            output_message = "Ошибка: введите корректно номер блюда";
        }

    }

    public void commandEcho(String str){
        output_message = ECHO_CONST + str;
    }


    /**
     * Команда /start в боте
     */
    public void commandStart(){
        output_message = START_CONST;
    }

    /**
     * Команда /help  в боте
     */
     public void commandHelp(){
        output_message = HELP_CONST;
     }

    /**
     * геттер для output_message
     * @return возвращает output_message
     */
    public String getOutputMassage(){
         return output_message;
     }

    /**
     * Реализует логику бота
     * @param message_text переменная с текстом сообщения пользователя
     */

     public void logic(String message_text, Long chat_id) throws IOException, ParseException {

         switch (message_text) {
             case ("/help"):
                 commandHelp();
                 break;

             case ("/start"):
                 commandStart();
                 break;

             case("/order"):
                 commandOrder(chat_id);
                 break;

             case("/menu"):
                 menuCalling();
                 addToCart(message_text);

                 break;








             default:
                 commandEcho(message_text);
                 break;
         }
     }

    /**
     * Пример работы с Order и ListOfOrders
     * @param chat_id chat id
     */
    private void commandOrder(Long chat_id){
        Order order = new Order(chat_id);

        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        listOfOrders.putOrder(order);

        output_message = order.formMessageForClient();
    }
}
