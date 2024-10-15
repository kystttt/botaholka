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
    private TextHandler textHandler = new TextHandler();

    @BeforeEach
    public void setUp() throws IOException {
        textHandler = new TextHandler();
        JSONObject menuJson = new JSONObject();
        menuJson.put("Шаурма", 250L);
        menuJson.put("Напиток", 140L);
        menuJson.put("ЛюляКебаб", 170L);

        try (FileWriter file = new FileWriter("src/main/resources/menu.json")) {
            file.write(menuJson.toJSONString());
        }
    }
        @Test
        public void testMenuCalling() {
            // Вызываем метод формирования меню
            textHandler.menuCalling();
            MenuList mnlst = MenuList.INSTANCE;
            assertEquals("ЛюляКебаб - 170 рублей", mnlst.getMenulist().get(1));
            assertEquals("Напиток - 140 рублей", mnlst.getMenulist().get(2));
            assertEquals("Шаурма - 250 рублей", mnlst.getMenulist().get(3));
        }
    }

