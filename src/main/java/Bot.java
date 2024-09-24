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

                TextHandler textHandler = new TextHandler();
                switch (message_text) {
                    case ("/help"):
                        textHandler.сommandHelp();
                        break;

                    case ("/start"):
                        textHandler.сommandStart();
                        break;

                    default:
                        textHandler.commandEcho(message_text);
                        break;
                }
                SendMessage message = SendMessage
                        .builder()
                        .chatId(chat_id)
                        .text(textHandler.getOutputMassage())
                        .build();
                try {
                    telegramClient.execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }