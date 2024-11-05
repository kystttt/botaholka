import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storages.ListCart;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестируем три метода: добавления в корзину, удаления и просмотра корзины
 */
public class ListCartTest {
    private ListCart testCart;

    @BeforeEach
    public void setup() {
        testCart = new ListCart();
    }

    /**
     * Проверяем добавляет ли метод в корзину
     */
    @Test
    public void addTest() {
        testCart.add("Шаурма");
        assertEquals(1, testCart.size());
    }

    /**
     * Тест для метода, который удаляет элемент из массива корзины
     */
    @Test
    public void removeTest() {
        testCart.add("Шаурма");
        testCart.remove(0);
        assertEquals(0, testCart.size());
    }
}