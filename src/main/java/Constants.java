/**
 * Класс для Констант
 */
public class Constants {
    public final static String START_CONST = """
                                Добро пожаловать в бота
                                """;

    public final static String ERROR_BRANCH = """
            В качестве кого вы бы хотели продолжить?
            /seller - продавец
            /buyer - покупатель
            """;

    public final static String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветствие пользователя
            
            /listoforders - просмотр текущих заказов
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;

    public final static String FUNCS_FOR_LIST_OF_ORDERS_BUYER = """
            Ваши функции:
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;

    public final static String ERROR_COMMAND = "Введите корректную команду, для списка всех команд - /help";


}
