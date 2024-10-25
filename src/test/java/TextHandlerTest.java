//import MenuLogic.Menu;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.json.simple.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
///**
// * <p><strong>Тесты для TextHandler</strong></p>
// *
// * <figcaption>Тестирование Функций:</figcaption>
// * <ul>
// *      <li>commandListOfOrders</li>
// *       <li>commandDuplicate</li>
// *       <li>commandCancelOrder</li>
// * </ul>
// *
// */
//public class TextHandlerTest {
//
//    private ListOfOrders listOfOrders = new ListOfOrders();
//    private Menu menu;
//
//    /**
//     * Обнуляет ListOfOrders и добавляет в menu.json несколько пунктов из меню
//     */
//    @BeforeEach
//    void resetListOfOrders() {
//        listOfOrders.clearList();
//        menu = new Menu();
//        menu.addFoodItem("Шаурма Большая", 100);
//    }
//
//    /**
//     *Тест для команды отображения текущих заказов
//     */
//    @Test
//    void commandListOfOrdersTest(){
//        TextHandler textHandler = new TextHandler(listOfOrders, new MenuList(), menu);
//
//        Order order = new Order((long)1);
//        order.addToArr("Шаурма Большая");
//        listOfOrders.putOrder(order);
//
//        String result = textHandler.getOutputMassage("/listoforders", (long)1);
//
//        String expected = """
//                Ваши заказы:
//                Заказ №1
//                Шаурма Большая - 100 руб.
//                Итого: 100 руб.
//
//                Ваши функции:
//                /duplicate “Номер заказа” - повторить заказ
//                /cancel “Номер заказа” - отменить заказ
//                """;
//        Assertions.assertEquals(expected, result);
//    }
//
//    /**
//     * Тест для команды дублирования определенного заказа
//     */
//    @Test
//    void commandDuplicateTest(){
//        TextHandler textHandler = new TextHandler(listOfOrders, new MenuList());
//
//        Order order = new Order((long)1);
//        order.addToArr("Шаурма Большая");
//        listOfOrders.putOrder(order);
//
//        textHandler.getOutputMassage("/duplicate 1", (long)1);
//
//        Assertions.assertEquals(2, listOfOrders.getHashMap().size());
//    }
//
//    /**
//     * Тест для команды удаления заказа из ListOfOrders
//     */
//    @Test
//    void commandCancelOrderTest(){
//        TextHandler textHandler = new TextHandler(listOfOrders, new MenuList());
//
//        Order order = new Order((long)1);
//        order.addToArr("Шаурма Большая");
//        listOfOrders.putOrder(order);
//
//        String result_text = textHandler.getOutputMassage("/cancel 1", (long)1);
//        String expected_text = "Заказ №" + (long)1 + " удалён ";
//
//        Assertions.assertEquals(expected_text, result_text);
//        Assertions.assertNull(listOfOrders.getValue(1));
//    }
//}
