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

    private String output_message;
    /**
     * Метод, который работает с текстом
     */

    public void commandEcho(String str){
        output_message = str;
    }

    /**
     * Команда /start в боте
     */
    public void сommandStart(){
        output_message = START_CONST;
    }

    /**
     * Команда /help  в боте
     */
     public void сommandHelp(){
        output_message = HELP_CONST;
     }

    /**
     * геттер для output_message
     * @return возвращает output_message
     */
    public String getOutputMassage(){
         return output_message;
     }

     public void Logic(String message_text){
         switch (message_text) {
             case ("/help"):
                 сommandHelp();
                 break;

             case ("/start"):
                 сommandStart();
                 break;

             default:
                 commandEcho(message_text);
                 break;
         }
     }
}
