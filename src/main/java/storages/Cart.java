package storages;

import java.util.ArrayList;
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
