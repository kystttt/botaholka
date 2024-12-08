import bots.DiscordBot;
import bots.TGBot;
import logic.BotLogic;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) throws Exception {
        BotLogic logic = new BotLogic();

//  До лучших времён(
//        String dsToken = System.getenv("DS_TOKEN");
//        JDA api = JDABuilder.createDefault(dsToken).build();
//        api.addEventListener(new DiscordBot(logic));

        String botToken = System.getenv("TG_TOKEN");
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new TGBot(botToken, logic));
            Thread.currentThread().join();
        } catch (Exception e) {
            throw new Exception("Тг бот не запустился");
        }

    }
}