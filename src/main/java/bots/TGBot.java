package bots;

import logic.BotLogic;
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
        private final BotLogic logic;

        public TGBot(String botToken, BotLogic logic) {
            telegramClient = new OkHttpTelegramClient(botToken);

            this.logic = logic;
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

                String output_message = logic.processMessage(message_text, chat_id);

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