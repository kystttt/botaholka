import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Тест для команды /menu
 */
public class TestCommandMenu {
    private MenuList testMenuList;
    private ListOfOrders testListOfOrders;
    private TextHandler textHandler;

    @BeforeEach
    public void setUp() throws IOException {
        testMenuList = new MenuList();
        testListOfOrders = new ListOfOrders();
        textHandler = new TextHandler(testListOfOrders, testMenuList);
        JSONObject menuJson = new JSONObject();
        menuJson.put("Шаурма", 250L);
        menuJson.put("Напиток", 140L);
        menuJson.put("ЛюляКебаб", 170L);

        try (FileWriter file = new FileWriter("src/main/resources/menu.json")) {
            file.write(menuJson.toJSONString());
        }
    }

    /**
     * тестируем команду /menu
     */
    @Test
    public void testMenuCalling() {
        textHandler.menuCalling();
        assertEquals("ЛюляКебаб - 170 рублей", testMenuList.getMenulist().get(1));
        assertEquals("Напиток - 140 рублей", testMenuList.getMenulist().get(2));
        assertEquals("Шаурма - 250 рублей",testMenuList.getMenulist().get(3));
    }
}

