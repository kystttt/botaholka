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
//        TextHandler textHandler = new TextHandler(listOfOrders, cart, new MenuList(), menu);
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
//TODO доделай свои тесты, я пока свое вставлю, сделай так, чтобы ничего не сломалось потом и удали TODO)
import MenuLogic.Menu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextHandlerTest{
    private Cart testCart;
    private TextHandler textHandler;
    private ListOfOrders testListOfOrders;
    private Menu testMenu;
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


    @Test
    public void testAddToCart_ValidDishIndex() {
        textHandler.setPrevCommand("/menu");
        textHandler.getOutputMassage("Шаурма", chat_id);
        textHandler.viewCart();
        assertEquals("Ваш заказ:\n1. Шаурма - 220 рублей\n",
                textHandler.getOutputMassage("/cart",chat_id));
        textHandler.setPrevCommand("/menu");
        assertEquals(1, testCart.getCartSize());
        assertEquals("Блюдо добавлено в корзину:\nШаурма - 220 рублей\nПосмотреть вашу корзину /cart",
                textHandler.getOutputMassage("Шаурма",chat_id));
        textHandler.setPrevCommand("/delete");
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
        textHandler.setPrevCommand("/menu");
        textHandler.getOutputMassage("5", chat_id);
        assertEquals(0, testCart.getCartSize());
        assertEquals("Ошибка: такого блюда нет в меню.", textHandler.getOutputMassage("5", chat_id));
    }


    /**
     * Тест для команды /makeOrder
     */
    @Test
    public void testMakeOrder(){
        textHandler.setPrevCommand("/menu");
        textHandler.getOutputMassage("Шаурма", chat_id);
        assertEquals("Ваш заказ сформирован", textHandler.getOutputMassage("/makeOrder",chat_id));
        assertEquals(0, testCart.getCartSize());
        textHandler.deleteFromCart("0");
        assertEquals("Корзина пуста", textHandler.getOutputMassage("/makeOrder",chat_id));
    }

}



