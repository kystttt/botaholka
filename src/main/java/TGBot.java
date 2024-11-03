    import fsm.cfg.TextHandler;
    import menu.*;
    import storages.ListOfOrders;
    import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
    import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.objects.Update;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import org.telegram.telegrambots.meta.generics.TelegramClient;
    import storages.Cart;
    import utils.Constants;

    /**
     * Создает телеграм бота, принимает и отправляет сообщения
     */
    public class TGBot implements LongPollingSingleThreadUpdateConsumer {
        private final TelegramClient telegramClient;
        private final TextHandler textHandler;
        Menu menu;
        Cart cart;
        ListOfOrders listOfOrders;

        public TGBot(String botToken) {
            telegramClient = new OkHttpTelegramClient(botToken);
            listOfOrders = new ListOfOrders();
            cart = new Cart();
            menu = new MenuImpl(Constants.MENU_FILENAME_CONST);
            this.textHandler =  new TextHandler(listOfOrders, cart, menu);
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

                String output_message = textHandler.processMessage(message_text, chat_id);

                try {
                    telegramClient.execute(SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(output_message)
                            .build());
                } catch (TelegramApiException e) {
                    System.err.println("Не удалось отправить сообщение\nchatId: " +
                                        chat_id +
                                        "\noutput_message: " +
                                        output_message);
                    e.printStackTrace();
                }
            }
        }
    }