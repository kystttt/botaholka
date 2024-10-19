import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Тестируем три метода: добавления в корзину, удаления и просмотра корзины
 */
public class TestAddCartAndCartAndDelete {

    private TextHandler textHandler;
    private MenuList testMenuList;
    private ListOfOrders testListOfOrders;
    Long chat_id = 13245L;

    @BeforeEach
    public void setup() {
        testMenuList = new MenuList();
        testListOfOrders = new ListOfOrders();
        textHandler = new TextHandler(testListOfOrders, testMenuList);
        testMenuList.getMenulist().put(1, "Шаурма - 250 рублей");
        testMenuList.getMenulist().put(2, "Напиток - 140 рублей");
    }

    @Test
    public void testAddToCart_ValidDishIndex() {
/**
 * Проверяем добавление, удаление и вывод
 */
        testMenuList.setPrevCommand("/menu");
        textHandler.getOutputMassage("1", chat_id);
        assertEquals(1, testMenuList.getCartSize());
        assertTrue(testMenuList.getCart().contains("1. Шаурма - 250 рублей"));
        assertEquals("Блюдо добавлено в корзину:\n1. Шаурма - 250 рублей\nПосмотреть вашу корзину /cart",
                textHandler.getOutputMassage("1",chat_id));
        testMenuList.setPrevCommand("/delete");
        textHandler.getOutputMassage("0", chat_id);
        textHandler.getOutputMassage("0", chat_id);
        assertEquals(0, testMenuList.getCartSize());
        textHandler.viewCart();
        assertEquals("Корзина пуста", textHandler.getOutputMassage("/cart", chat_id));

    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        testMenuList.setPrevCommand("/menu");
        textHandler.getOutputMassage("5", chat_id);
        assertEquals(0, testMenuList.getCartSize());
        assertEquals("Ошибка: такого блюда нет в меню.", textHandler.getOutputMassage("5", chat_id));
    }

    /**
     * Достаем блюдо с неправильным индексом
     */
    @Test
    public void testAddToCart_NonNumericInput() {
        textHandler.addToCart("abc");
        assertEquals(0, testMenuList.getCartSize());
        assertEquals("Ошибка: индекс блюда должен быть числом.", textHandler.getOutputMassage("abc", chat_id));
    }

    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder(){
        testMenuList.setPrevCommand("/menu");
        textHandler.getOutputMassage("1", chat_id);
        assertEquals("Ваш заказ сформирован", textHandler.getOutputMassage("/makeOrder",chat_id));
        assertEquals(0, testMenuList.getCartSize());
        textHandler.deleteFromCart("0");
        assertEquals("Корзина пуста", textHandler.getOutputMassage("/makeOrder",chat_id));
    }
}