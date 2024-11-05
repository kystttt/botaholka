import java.util.ArrayList;
import java.util.List;

/**
 * Реализация {@link Cart}
 */
public class ListCart implements Cart{
    private final List<String> cart;

    /**
     * Конструктор класса MenuList
     */
    public ListCart() {
        cart = new ArrayList<>();
    }

    @Override
    public String get(int index) {
        if (index >= 0 && index < cart.size()) {
            return cart.get(index);
        }
        return Constants.ERROR_INDEX_CONST;
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public void add(String dish) {
        cart.add(dish);
    }

    @Override
    public int size() {
        return cart.size();
    }

    @Override
    public void remove(int index) {
        if (index >= 0 && index < cart.size()) {
            cart.remove(index); // Удаляем элемент по индексу
        }
    }
}
