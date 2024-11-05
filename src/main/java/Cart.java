public interface Cart {
    String get(int index);

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
