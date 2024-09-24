import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest{
    private TextHandler textHandler;

    @BeforeEach
    public void create(){
        textHandler = new TextHandler();
    }

    /**
     * Тест с обычной строкой
     */
    @Test
    public void commandEchotTest_1(){
        textHandler.commandEcho("123");

        String expectedMessage = "Вы ввели: 123";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    /**
     * Тест со специальными символами
     */
    @Test
    public void commandEchoTest_2(){
        textHandler.commandEcho("!@#$%^&*()");

        String expectedMessage = "Вы ввели: !@#$%^&*()";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    /**
     * Тест с пустой строкой
     */
    @Test
    public void commandEchoTest_3(){
        textHandler.commandEcho("");

        String expectedMessage = "Вы ввели: ";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    /**
     * Тест с большой строкой
     */
    @Test
    public void commandStartTest_4(){
        textHandler.commandEcho("a".repeat(10000));

        String expectedMessage = "Вы ввели: " + "a".repeat(10000);
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }
}