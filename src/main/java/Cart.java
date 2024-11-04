import java.util.ArrayList;
import java.util.List;


/**
 * Класс, в котором хранится корзина
 */
public class Cart {
    private final List<String> cart;


    /**
     * Конструктор класса MenuList
     */
    public Cart() {
        cart = new ArrayList<>();
    }


    /**
     * Возвращает значение корзины по ключу индекс блюда.
     */
    public String getCartValue(int index) {
        if (index >= 0 && index < cart.size()) {
            return cart.get(index);
        }
        return Constants.ERROR_INDEX_CONST;
    }

    /**
     * Очищаеь корзину
     */
    public void cartClear() {
        cart.clear();
    }

    /**
     * Возвращает корзину как список
     */
    public List<String> getCart() {
        return new ArrayList<>(cart);
    }


    /**
     * Добавляет в корзину
     *
     * @param dish
     */
    public void addToCart(String dish) {
        cart.add(dish);
    }

    /**
     * Возвращает количество элементов в корзине
     *
     * @return
     */
    public int getCartSize() {
        return cart.size(); // Возвращаем размер ArrayList
    }

    /**
     * Удаляет блюдо из корзины
     *
     * @param index
     */
    public void removeFromCart(int index) {
        if (index >= 0 && index < cart.size()) {
            cart.remove(index); // Удаляем элемент по индексу
        }
    }
}
