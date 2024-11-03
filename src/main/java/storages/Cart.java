package storages;

import java.util.ArrayList;


/**
 * Корзина
 */
public class Cart {
    private final ArrayList<String> cart;

    /**
     * Конструктор класса MenuList
     */
    public Cart() {
        cart = new ArrayList<>();
    }


    /**
     * Возвращает значение корзины по ключу индекс блюда.
     * В случае, если индекса не существует, возвращает константу ERROR_INDEX_CONST = ""Такого индекса не существует\n";"
     */
    public String getCartValue(int index) {
        if (index >= 0 && index < cart.size()) {
            return cart.get(index);
        }
        return Constants.ERROR_INDEX_CONST;
    }

    /**
     * Очищаешь корзину
     */
    public void cartClear() {
        cart.clear();
    }

    /**
     * Возвращает корзину как список
     */
    public ArrayList<String> getCart() {
        return cart;
    }


    /**
     * Добавляет в корзину
     */
    public void addToCart(String dish) {
        cart.add(dish);
    }

    /**
     * Возвращает количество элементов в корзине
     */
    public int getCartSize() {
        return cart.size(); // Возвращаем размер ArrayList
    }

    /**
     * Удаляет блюдо из корзины
     */
    public void removeFromCart(int index) {
        if (index >= 0 && index < cart.size()) {
            cart.remove(index); // Удаляем элемент по индексу
        }
    }
}
