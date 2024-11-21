package storages;

/**
 * Корзина
 */
//TODO Сделай так чтобы корзина была не общая для всех, а для каждого пользователя своя
public interface Cart {
    String get(int index);

    /**
     * Очищает корзину
     */
    void clear();

    /**
     * Добавляет блюдо в корзину
     */
    void add(String dish);

    /**
     * Возвращает размер корзины
     */
    int size();

    /**
     * Удаляет элемент из корзины по его индексу
     */
    void remove(int index);
}
