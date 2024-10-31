import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import menu.*;


/**
 * Тестируем три метода: добавления в корзину, удаления и просмотра корзины
 */
public class CartTest {

    private TextHandler textHandler;
    private Cart testCart;
    private Menu testMenu;
    private ListOfOrders testListOfOrders;
    Long chat_id = 13245L;

    @BeforeEach
    public void setup() {
        testCart = new Cart();

    }


    /**
     * Проверяем добавляет ли метод в корзину
     */
    @Test
    public void addToCartTest() {
        testCart.addToCart("Шаурма");
        assertEquals(1, testCart.getCartSize());
    }


    /**
     * Тест для метода, который удаляет элемент из массива корзины
     */

    @Test
    public void removeFromCartTest() {
        testCart.addToCart("Шаурма");
        testCart.removeFromCart(0);
        assertEquals(0, testCart.getCartSize());
    }
}