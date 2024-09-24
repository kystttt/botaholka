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
     * @param str
     * @return возвращает текст пользователя
     */

    public void echo(String str){
        output_message = str;
    }

    /**
     * Команда /start в боте
     */
    public void CommandStart(){
        output_message = START_CONST;
    }

    /**
     * Команда /help  в боте
     */
     public void CommandHelp(){
        output_message = HELP_CONST;
     }

    /**
     * геттер для output_message
     * @return возвращает output_message
     */
    public String getOutputMassage(){
         return output_message;
     }
}
