import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Тестируем три метода: добавления в корзину, удаления и просмотра корзины
 */
public class TestAddCartAndCartAndDelete {

    private TextHandler textHandler;
    private MenuList testMenuList;
    Long chat_id = 13245L;

    @BeforeEach
    public void setup() {
        textHandler = new TextHandler();
        testMenuList = MenuList.INSTANCE;
        testMenuList.getMenulist().put(1, "Шаурма - 250 рублей");
        testMenuList.getMenulist().put(2, "Напиток - 140 рублей");
    }

    @Test
    public void testAddToCart_ValidDishIndex() {
/**
 * Проверяем добавление, удаление и вывод
 */
        textHandler.addToCart("1");
        assertEquals(1, testMenuList.getCartSize());
        assertTrue(testMenuList.getCart().contains("1. Шаурма - 250 рублей"));
        assertEquals("Блюдо добавлено в корзину:\n1. Шаурма - 250 рублей", textHandler.getOutputMassage());
        textHandler.deleteFromCart("0");
        assertEquals(0, testMenuList.getCartSize());
        textHandler.viewCart();
        assertEquals("Корзина пуста", textHandler.getOutputMassage());
        //assertEquals("Корзина пуста", textHandler.getOutputMassage());
    }

    /**
     * Пытаемся добавить несуществующую блюдо
     */
    @Test
    public void testAddToCart_InvalidDishIndex() {
        textHandler.addToCart("5");
        assertEquals(0, testMenuList.getCartSize());
        assertEquals("Ошибка: такого блюда нет в меню.", textHandler.getOutputMassage());
    }

    /**
     * Достаем блюдо с неправильным индексом
     */
    @Test
    public void testAddToCart_NonNumericInput() {
        textHandler.addToCart("abc");
        assertEquals(0, testMenuList.getCartSize());
        assertEquals("Ошибка: индекс блюда должен быть числом.", textHandler.getOutputMassage());
    }

    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder(){
        textHandler.addToCart("1");
        textHandler.makeOrder(chat_id);
        assertEquals("Ваш заказ сформирован", textHandler.getOutputMassage());
        assertEquals(0, testMenuList.getCartSize());
        textHandler.deleteFromCart("1");
        textHandler.makeOrder(chat_id);
        assertEquals("Корзина пуста", textHandler.getOutputMassage());
    }
}