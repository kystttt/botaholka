import handlers.TextHandler;
import menu.*;
import storages.ListOfOrders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storages.Cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

/**
 * Тест для команды /menu
 */
public class CommandMenuTest {
    private TextHandler textHandler;
    Long chat_id = 13245L;

    @BeforeEach
    public void setUp() throws IOException {
        ListOfOrders testListOfOrders = new ListOfOrders();
        Cart testCart = new Cart();
        Menu testMenu = new MenuImpl();
        textHandler = new TextHandler(testListOfOrders, testCart, testMenu);
        testMenu.addFoodItem("ЛюляКебаб", 260);
        testMenu.addFoodItem("Напиток", 110);
        testMenu.addFoodItem("Шаурма", 220);

    }
//TODO
//    /**
//     * тестируем команду /menu
//     */
//    @Test
//    public void testMenuCalling() {
//        assertEquals("Меню: \n1. ЛюляКебаб - 260 рублей\n2. Напиток - 110 рублей\n" +
//                "3. Шаурма - 220 рублей\nВведите название блюда, которое хотите заказать: ",
//                textHandler.processMessage("/menu", chat_id));
//    }
}

