import menu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

/**
 * Тест для команды /menu
 */
public class CommandMenuTest {
    private Menu testMenu;
    private Cart testCart;
    private ListOfOrders testListOfOrders;
    private TextHandler textHandler;
    Long chat_id = 13245L;

    @BeforeEach
    public void setUp() throws IOException {
        testListOfOrders = new ListOfOrders();
        testCart = new Cart();
        testMenu = new MenuImpl();
        textHandler = new TextHandler(testListOfOrders, testCart, testMenu);
        testMenu.addFoodItem("ЛюляКебаб", 260);
        testMenu.addFoodItem("Напиток", 110);
        testMenu.addFoodItem("Шаурма", 220);

    }

    /**
     * тестируем команду /menu
     */
    @Test
    public void testMenuCalling() {
        assertEquals("Меню: \n1. ЛюляКебаб - 260 рублей\n2. Напиток - 110 рублей\n" +
                "3. Шаурма - 220 рублей\nВведите название блюда, которое хотите заказать: ",
                textHandler.proccessMessage("/menu", chat_id));
    }
}

