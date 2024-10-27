import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import MenuLogic.Menu;


/**
 * Тестируем три метода: добавления в корзину, удаления и просмотра корзины
 */
public class CommandAddToCARTCommandCartAndCommandDeleteTest {

    private TextHandler textHandler;
    private Cart testCart;
    private Menu testMenu;
    private ListOfOrders testListOfOrders;
    Long chat_id = 13245L;

    @BeforeEach
    public void setup() {
        testCart = new Cart();
        testMenu = new Menu();
        testListOfOrders = new ListOfOrders();
        textHandler = new TextHandler(testListOfOrders, testCart, testMenu);
        testMenu.getHashMap().put("Шаурма", 220);
        testMenu.getHashMap().put("Напиток", 110);
    }
/**
 * Проверяем добавление, удаление и вывод
 */
    @Test
    public void testAddToCart_ValidDishIndex() {
        testCart.setPrevCommand("/menu");
        textHandler.getOutputMassage("Шаурма", chat_id);
        textHandler.viewCart();
        assertEquals("Ваш заказ:\n1. Шаурма - 220 рублей\n",
                textHandler.getOutputMassage("/cart",chat_id));
        testCart.setPrevCommand("/menu");
        assertEquals(1, testCart.getCartSize());
        assertTrue(testCart.getCart().contains("Шаурма - 220 рублей"));
        assertEquals("Блюдо добавлено в корзину:\nШаурма - 220 рублей\nПосмотреть вашу корзину /cart",
                textHandler.getOutputMassage("Шаурма",chat_id));
        testCart.setPrevCommand("/delete");
        textHandler.getOutputMassage("1", chat_id);
        textHandler.getOutputMassage("1", chat_id);
        assertEquals(0, testCart.getCartSize());
        textHandler.viewCart();
        assertEquals("Корзина пуста", textHandler.getOutputMassage("/cart", chat_id));

    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        testCart.setPrevCommand("/menu");
        textHandler.getOutputMassage("5", chat_id);
        assertEquals(0, testCart.getCartSize());
        assertEquals("Ошибка: такого блюда нет в меню.", textHandler.getOutputMassage("5", chat_id));
    }


    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder(){
        testCart.setPrevCommand("/menu");
        textHandler.getOutputMassage("Шаурма", chat_id);
        assertEquals("Ваш заказ сформирован", textHandler.getOutputMassage("/makeOrder",chat_id));
        assertEquals(0, testCart.getCartSize());
        textHandler.deleteFromCart("0");
        assertEquals("Корзина пуста", textHandler.getOutputMassage("/makeOrder",chat_id));
    }
}
