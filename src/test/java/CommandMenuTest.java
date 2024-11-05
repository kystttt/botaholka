//TODO
//import menu.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Тест для команды /menu
// */
//public class CommandMenuTest {
//    private TextHandler textHandler;
//    Long chat_id = 13245L;
//
//    @BeforeEach
//    public void setUp() {
//        Menu<String, Integer> testMenu = new MenuImpl();
//        testMenu.addFoodItem("ЛюляКебаб", 260);
//        testMenu.addFoodItem("Напиток", 110);
//        testMenu.addFoodItem("Шаурма", 220);
//        textHandler = new TextHandler(testMenu);
//    }
//
//    /**
//     * тестируем команду /menu
//     */
//    @Test
//    public void testMenuCalling() {
//        assertEquals("""
//                        Меню:\s
//                        1. ЛюляКебаб - 260 рублей
//                        2. Напиток - 110 рублей
//                        3. Шаурма - 220 рублей
//                        Введите название блюда, которое хотите заказать:\s""",
//                textHandler.processMessage("/menu", chat_id));
//    }
//}
//
