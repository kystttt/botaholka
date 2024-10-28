import menu.MenuImpl;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {
        ListOfOrders listOfOrders = new ListOfOrders();
        MenuList menuList = new MenuList();
        MenuImpl menu = new MenuImpl(Constants.MENU_FILENAME_CONST);

        String botToken = System.getenv("TG_TOKEN");
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new TGBot(botToken, listOfOrders, cart, menu));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
