    import org.json.simple.parser.ParseException;
    import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
    import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.objects.Update;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import org.telegram.telegrambots.meta.generics.TelegramClient;

    import java.io.IOException;

    /**
     * Создает телеграм бота, принимает и отправляет сообщения
     */
    public class TGBot implements LongPollingSingleThreadUpdateConsumer {
        private final TelegramClient telegramClient;

        public TGBot(String botToken) {
            telegramClient = new OkHttpTelegramClient(botToken);
        }

        /**
         * Метод принимает сообщение, обрабатывает и отправляет обратно пользователю
         * @param update объект с сообщением пользователя
         */
        @Override
        public void consume(Update update) {
            if (update.hasMessage() && update.getMessage().hasText()) {
                String message_text = update.getMessage().getText();
                long chat_id = update.getMessage().getChatId();

                TextHandler textHandler = new TextHandler();
                try {
                    textHandler.logic(message_text, chat_id);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }

                try {
                    telegramClient.execute(SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(textHandler.getOutputMassage())
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }

    }