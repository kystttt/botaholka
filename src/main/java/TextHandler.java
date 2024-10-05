import org.json.simple.parser.ParseException;

import java.io.IOException;

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
    private int mass_id = 1;
    private String output_message;
    /**
     * Метод, который работает с текстом
     */

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

             default:
                 commandEcho(message_text);
                 break;
         }
     }

    /**
     * Пример работы с Order и ListOfOrders
     * @param chat_id
     * @throws IOException
     * @throws ParseException
     */
    private void commandOrder(Long chat_id) throws IOException, ParseException {
        Order order = new Order(chat_id);

        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        listOfOrders.putOrder(order);

        output_message = order.formMessageForClient();
    }
}
