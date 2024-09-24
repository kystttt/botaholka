import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest{
    private TextHandler textHandler;

    @BeforeEach
    public void create(){
        textHandler = new TextHandler();
    }

    @Test
    public void commandStartTest_1(){
        textHandler.commandEcho("123");

        String expectedMessage = "Вы ввели: 123";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    @Test
    public void commandStartTest_2(){
        textHandler.commandEcho("!@#$%^&*()");

        String expectedMessage = "Вы ввели: !@#$%^&*()";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    @Test
    public void commandStartTest_3(){
        textHandler.commandEcho("");

        String expectedMessage = "Вы ввели: ";
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }

    @Test
    public void commandStartTest_4(){
        textHandler.commandEcho("a".repeat(10000));

        String expectedMessage = "a".repeat(10000);
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }
}