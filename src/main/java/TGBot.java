    import menu.MenuImpl;
    import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
    import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.objects.Update;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import org.telegram.telegrambots.meta.generics.TelegramClient;

    /**
     * Создает телеграм бота, принимает и отправляет сообщения
     */
    public class TGBot implements LongPollingSingleThreadUpdateConsumer {
        private final TelegramClient telegramClient;
        private final MenuImpl menu;

        private ListOfOrders listOfOrders;
        private MenuList menuList;

        public TGBot(String botToken, ListOfOrders listOfOrders, MenuList menuList, MenuImpl menu) {
            this.listOfOrders = listOfOrders;
            this.menuList = menuList;

            telegramClient = new OkHttpTelegramClient(botToken);
            this.menu = menu;
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

                TextHandler textHandler = new TextHandler(listOfOrders, menuList, menu);
                String output_message = textHandler.getOutputMassage(message_text, chat_id);

                try {
                    telegramClient.execute(SendMessage
                            .builder()
                            .chatId(chat_id)
                            .text(output_message)
                            .build());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }