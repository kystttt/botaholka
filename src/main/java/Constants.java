public class Constants {
    public Constants(){}

    private final String START_CONST = """
                                Добро пожаловать в бота
                                """;

    private final String ERROR_BRANCH = """
            В качестве кого вы бы хотели продолжить?
            /seller - продавец
            /buyer - покупатель
            """;

    private final String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветствие пользователя
            
            /listoforders - просмотр текущих заказов
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;

    private final String FUNCS_FOR_LIST_OF_ORDERS_BUYER = """
            Ваши функции:
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;

    private final String ERROR_COMMAND = "Введите корректную команду, для списка всех команд - /help";

    public enum Types {
        START,
        ERROR_BRANCH,
        HELP,
        FUNCS_FOR_LIST_OF_ORDERS_BUYER,
        ERROR_COMMAND;
    }

    public String getConst(Types constantsTypes){
        return switch (constantsTypes) {
            case START -> START_CONST;
            case ERROR_BRANCH -> ERROR_BRANCH;
            case HELP -> HELP_CONST;
            case FUNCS_FOR_LIST_OF_ORDERS_BUYER -> FUNCS_FOR_LIST_OF_ORDERS_BUYER;
            case ERROR_COMMAND -> ERROR_COMMAND;
            default -> "Нету такой константы";
        };
    }
}
