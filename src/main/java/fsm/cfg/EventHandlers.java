package fsm.cfg;

import fsm.cfg.handlers.TextHandler;
import fsm.core.EventHandler;
import utils.Constants;

/**
 * Инициализация всех {@link EventHandler} для всех {@link fsm.core.Transition}
 */
public class EventHandlers {
    TextHandler textHandler;

    public EventHandlers(){
        textHandler = new TextHandler(Constants.MENU_FILENAME_CONST);
    }

    public EventHandler startHelp = (String messageText, long chatId) ->
            Constants.START_HELP;

    public EventHandler buyerHelp = (String messageText, long chatId) ->
            Constants.BUYER_HELP;

    public EventHandler listOfOrdersHelp = (String messageText, long chatId) ->
            textHandler.listOfOrders(chatId);

    public EventHandler cancelHelp = (String messageText, long chatId) ->
            Constants.CANCEL_HELP;

    public EventHandler cancelInt = (String messageText, long chatId) ->
            textHandler.cancelOrder(messageText, chatId);

    public EventHandler duplicateHelp = (String messageText, long chatId) ->
            Constants.DUPLICATE_HELP;

    public EventHandler duplicateInt = (String messageText, long chatId) ->
            textHandler.duplicate(messageText, chatId);

    public EventHandler menuHelp = (String messageText, long chatId) ->
            textHandler.menuCalling() + "\n" +
                    Constants.MENU_HELP;

    public EventHandler makeOrder = (String messageText, long chatId) ->
            textHandler.makeOrder(chatId);

    public EventHandler menuInt = (String messageText, long chatId) ->
            textHandler.addToCart(messageText, chatId) + "\n" +
                    textHandler.viewCart(chatId);

    public EventHandler deleteHelp = (String messageText, long chatId) ->
            Constants.DELETE_HELP;

    public EventHandler deleteInt = (String messageText, long chatId) ->
            textHandler.deleteFromCart(messageText, chatId);
}
