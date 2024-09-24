import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHelpTest{
    private TextHandler textHandler;

    @BeforeEach
    public void create(){
        textHandler = new TextHandler();
    }

    @Test
    public void commandStartTest(){
        textHandler.сommandHelp();

        String expectedMessage = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветсвие пользователя
            """;
        assertEquals(expectedMessage, textHandler.getOutputMassage());
    }
}