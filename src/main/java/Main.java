import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String botToken = System.getenv("TG_TOKEN");
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new TGBot(botToken));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
