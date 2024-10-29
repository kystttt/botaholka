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
    public final static String MENU_CONST = "Меню: \n";

    public final static String CART_EMPTY_CONST = "Корзина пуста";
    public final static String DISH_ADDED_CONST = "Блюдо добавлено в корзину:\n";
    public final static String ERROR_TYPE_CONST = "Ошибка: индекс блюда должен быть числом.";
    public final static String ERROR_UNDEFIND_NUM_CONST = "Ошибка: такого блюда нет в меню.";
    public final static String YOUR_ORDER_CONST = "Ваш заказ:\n";
    public final static String YOUR_CART_CONST = "\nПосмотреть вашу корзину /cart";
    public final static String SUCCES_DELETE_DISH_CONST = "Блюдо успешно удалено ";
    public final static String DELETE_OUT_MSG_CONST = "Введите номер блюда, которое хотите удалить: ";
    public final static String MAKED_ORDER_CONST = "Ваш заказ сформирован";
    public final static String ERROR_INDEX_CONST = "Такого индекса не существует\n";
    public final static String CHOOSE_CONST = "Введите название блюда, которое хотите заказать: ";

    public final static String MENU_FILENAME_CONST = "src/main/resources/menu.json";

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

    public final static String FUNCS_FOR_LIST_OF_ORDERS_BUYER = """
            Ваши функции:
            /duplicate “Номер заказа” - повторить заказ
            /cancel “Номер заказа” - отменить заказ
            """;

    public final static String ERROR_COMMAND = "Введите корректную команду, для списка всех команд - /help";


}
