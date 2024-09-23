import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            String output_message;
            switch (message_text) {
                case ("/help"):
                    output_message = """
                            Этот бот представляет собой барахолку МатМеха,
                            Список команд:
                            /help
                            /start
                            """;
                    break;

                case ("/start"):
                    output_message = """
                            Добро пожаловать на барахолку МатМеха
                            """;
                    break;

                default:
                    output_message = "Вы ввели:\"" + message_text + "\".";
                    break;
            }
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chat_id)
                    .text(output_message)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}