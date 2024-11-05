package handlers;

import fsm.core.EventHandler;
import utils.Constants;

public class EventHandlers {
    TextHandler textHandler;

    public EventHandlers(){
        textHandler = new TextHandler(Constants.MENU_FILENAME_CONST);
    }

    public EventHandler startHelp = (String messageText, long chatId) -> Constants.START_HELP;

    public EventHandler buyerHelp = (String messageText, long chatId) -> Constants.BUYER_HELP;

    public EventHandler listOfOrdersHelp = (String messageText, long chatId) -> textHandler.listOfOrders(chatId);

    public EventHandler cancelHelp;
    public EventHandler cancelInt;
    public EventHandler duplicateHelp;
    public EventHandler duplicateInt;
    public EventHandler menuHelp;
    public EventHandler makeOrder;
    public EventHandler menuInt;
    public EventHandler deleteHelp;
    public EventHandler deleteInt;
}
