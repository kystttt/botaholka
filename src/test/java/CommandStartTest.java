import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStartTest{
    private TextHandler textHandler;

    @BeforeEach
    public void create(){
        textHandler = new TextHandler();
    }

    @Test
    public void commandStartTest(){
        textHandler.CommandStart();

        String expectedMessage = """
                Добро пожаловать в бот
                """;
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }
}