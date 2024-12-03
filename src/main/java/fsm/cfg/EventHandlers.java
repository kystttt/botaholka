package fsm.cfg;

import fsm.cfg.handlers.TextHandler;
import fsm.core.EventHandler;
import utils.Constants;

import javax.xml.stream.util.XMLEventAllocator;

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
            textHandler.addToCart(messageText, chatId) + "\n";

    public EventHandler deleteHelp = (String messageText, long chatId) ->
            Constants.DELETE_HELP;

    public EventHandler deleteInt = (String messageText, long chatId) ->
            textHandler.deleteFromCart(messageText, chatId);

    public EventHandler cart = (String messageText, long chatId)->
            textHandler.viewCart(chatId);

    public EventHandler sellerHelp = (String messageText, long chatId)->
            Constants.SELLER_HELP;

    public EventHandler sellerOrders = (String messageText, long chatId)->
            textHandler.usersListOfOrders();

    public EventHandler nextStatus = (String messageText, long chatId)->
            Constants.NEXT_STATUS_HELP;

    public EventHandler nextStatusInt = (String messageText, long chatId)->
            textHandler.nextStatus(messageText, chatId);

    public EventHandler sellerOrder = (String messageText, long chatId)->
            Constants.CHOOSE_ORDER;

    public EventHandler sellerOrderInt = (String messageText, long chatId)->
            textHandler.sellerOrder(messageText, chatId);

    public EventHandler IntError = (String messageText, long chatId)->
            Constants.ERROR_TYPE_CONST;

}

