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
    private final String MENU_CONST = "Меню: \n";

    private final String CART_EMPTY_CONST = "Корзина пуста";
    private final String DISH_ADDED_CONST = "Блюдо добавлено в корзину:\n";
    private final String ERROR_TYPE_CONST = "Ошибка: индекс блюда должен быть числом.";
    private final String ERROR_UNDEFIND_NUM_CONST = "Ошибка: такого блюда нет в меню.";
    private final String YOUR_ORDER_CONST = "Ваш заказ:\n";
    private final String YOUR_CART_CONST = "\nПосмотреть вашу корзину /cart";
    private final String SUCCES_DELETE_DISH_CONST = "Блюдо успешно удалено ";
    private final String DELETE_OUT_MSG_CONST = "Введите номер блюда, которое хотите удалить: ";
    private final String MAKED_ORDER_CONST = "Ваш заказ сформирован";
    private final String ERROR_INDEX_CONST = "Такого индекса не существует\n";

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

    private final String CHOOSE_CONST = "Введите номер того, что хотите заказать: ";

    public enum Types {
        START,
        ERROR_BRANCH,
        HELP,
        FUNCS_FOR_LIST_OF_ORDERS_BUYER,
        ERROR_COMMAND,
        MENU,
        CHOOSE,
        CART_EMPTY,
        DISH_ADDED,
        ERROR_TYPE,
        ERROR_UNDEFIND_NUM,
        YOUR_ORDER,
        YOUR_CART,
        SUCCES_DELETE_DISH,
        DELETE_OUT_MSG,
        MAKED_ORDER,
        ERROR_INDEX;

    }

    public String getConst(Types constantsTypes){
        return switch (constantsTypes) {
            case START -> START_CONST;
            case ERROR_BRANCH -> ERROR_BRANCH;
            case HELP -> HELP_CONST;
            case FUNCS_FOR_LIST_OF_ORDERS_BUYER -> FUNCS_FOR_LIST_OF_ORDERS_BUYER;
            case ERROR_COMMAND -> ERROR_COMMAND;
            case MENU->MENU_CONST;
            case CHOOSE->CHOOSE_CONST;
            case CART_EMPTY->CART_EMPTY_CONST;
            case DISH_ADDED -> DISH_ADDED_CONST;
            case ERROR_TYPE -> ERROR_TYPE_CONST;
            case ERROR_UNDEFIND_NUM -> ERROR_UNDEFIND_NUM_CONST;
            case YOUR_ORDER -> YOUR_ORDER_CONST;
            case YOUR_CART->YOUR_CART_CONST;
            case SUCCES_DELETE_DISH -> SUCCES_DELETE_DISH_CONST;
            case DELETE_OUT_MSG -> DELETE_OUT_MSG_CONST;
            case MAKED_ORDER -> MAKED_ORDER_CONST;
            case ERROR_INDEX->ERROR_INDEX_CONST;

            default -> "Нету такой константы";
        };
    }
}
