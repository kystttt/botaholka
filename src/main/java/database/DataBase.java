package database;

public interface DataBase<T> {

    /**
     * Возвращает поле пользователя по его id
     * или null, если не смог подучить нужный столбец
     */
    T get(Long chatId);

    /**
     * Обновляет поля для пользователя
     * @param chatId id пользователя
     * @param t Новое значение поля
     * @return обновление поля прошло (1) - успешно,
     * (0) - не успешно
     */
    int set(Long chatId, T t);
}
