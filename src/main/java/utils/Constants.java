package utils;

/**
 * Класс для Констант
 */
public class Constants {
    public final static String START_CONST = """
                                Добро пожаловать в бота
                                """;
    public static final String HELP_CLONE = "Доступные методы:\n";

    public final static String START_HELP = """
            В качестве кого вы бы хотели продолжить?
            /seller - продавец
            /buyer - покупатель
            """;
    public final static String MENU_CONST = "Меню: \n";
    public final static String CART_EMPTY_CONST = "Корзина пуста\n/back - вернуться назад";
    public final static String DISH_ADDED_CONST = "Блюдо добавлено в корзину:\n";
    public final static String ERROR_TYPE_CONST = "Ошибка: индекс должен быть числом.";
    public final static String ERROR_UNDEFINED_NUMB_CONST = "Ошибка: такого блюда нет в меню.";
    public final static String YOUR_ORDER_CONST = "Ваша корзина:\n";
    public final static String YOUR_CART_CONST = "\nПосмотреть вашу корзину /cart";
    public final static String SUCCESS_DELETE_DISH_CONST = "Блюдо успешно удалено ";
    public final static String DELETE_OUT_MSG_CONST = "Введите номер блюда, которое хотите удалить: ";
    public final static String MADE_ORDER_CONST = "Ваш заказ сформирован";
    public final static String ERROR_INDEX_CONST = "Такого индекса не существует\n";
    public final static String CHOOSE_CONST = "Введите название блюда, которое хотите заказать: ";
    public final static String NO_AVAILABLE_ORDERS = "У вас нету активных заказов";
    public final static String MENU_FILENAME_CONST = "src/main/java/utils/menu.json";
    public final static String NEXT_STATUS_HELP =
            """
            Введите номер заказа, статус которого вы хотите изменить
            """ + HELP_CLONE +  "/back - вернуться назад\n";


    public final static String SELLER_HELP =
             """
             /orders - просмотр всех текущих заказов
             /back - вернуться к назад к выбору
             """;

    public final static String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветствие пользователя
            /menu - выводит доступные блюда для заказа, после этого введите номер блюда, которое хотите заказать
            /cart - выводит содержимое корзины
            /delete - удаляет позицию в корзине по ее индексу, введите индекс позиции, которую хотите удалить из корзины
            /order - собирает заказ на основе того, что лежит в корзине
            /listoforders - просмотр текущих заказов
            /duplicate “Номер заказа” - повторить заказ
            /cancel “Номер заказа” - отменить заказ
            """;


    public final static String FUNCS_FOR_LIST_OF_ORDERS_BUYER = HELP_CLONE +
            """
            /duplicate “Номер заказа” - повторить заказ
            /cancel “Номер заказа” - отменить заказ
            /back - вернуться к назад
            """;

    public final static String ERROR_COMMAND = "Введите корректную команду, для списка всех команд - /help";

    public final static String BUYER_HELP = HELP_CLONE +
            """
             /menu - выводит доступные блюда для заказа
             /listoforders - просмотр текущих заказов
             /back - вернуться к назад
             """;

    public static final String CANCEL_HELP =
            "(int) - для отмены конкретного(int) заказа\n\n" +
                    HELP_CLONE +
            "/back - вернуться к списку моих заказов";

    public static final String DUPLICATE_HELP =
            "(int) - для дублирования конкретного(int) заказа\n\n" +
            HELP_CLONE +
            "/back - вернуться к списку моих заказов";

    public static final String MENU_HELP =
            "(название блюда) - для выбора блюда из меню конкретного(int) заказа\n\n" +
            HELP_CLONE +
            "/order - оформить заказ\n" +
            "/cart - просмотр корзины\n" +
            "/back - отмена заказа";

    public static final String DELETE_HELP =
            "(int) - для удаления конкретной(int) позиции из корзины\n\n" +
            HELP_CLONE +
            "/back - вернуться к списку моих заказов";

    public static final String CHOOSE_ORDER = "Выберите заказ\n\n" + HELP_CLONE
            ;
}
