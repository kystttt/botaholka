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
            /start - приветсвие пользователя
            """;

    private final String ECHO_CONST = "Вы ввели: ";

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

     public void logic(String message_text){
         switch (message_text) {
             case ("/help"):
                 commandHelp();
                 break;

             case ("/start"):
                 commandStart();
                 break;

             default:
                 commandEcho(message_text);
                 break;
         }
     }
}
