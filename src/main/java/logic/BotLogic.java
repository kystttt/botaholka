package logic;

import fsm.cfg.Event;
import fsm.core.FiniteStateMachine;
import fsm.core.State;
import storages.api.StateStorage;
import storages.core.StateFromDataBase;


/**
 * Логика бота для обработки сообщений
 */
public class BotLogic {
    FiniteStateMachine fsm;
    StateStorage stateStorage;


    public BotLogic() {
        fsm = new FiniteStateMachine();
        stateStorage = new StateFromDataBase();
    }

    BotLogic(StateStorage stateStorage) {
        fsm = new FiniteStateMachine();
        this.stateStorage = stateStorage;
    }

    /**
     * Обработка текста
     *
     * @param messageText Сообщение пользователя
     * @param chatId Id пользователя
     * @return Строку с ответом бота
     */
    public String processMessage(String messageText, Long chatId) {
        State userState = stateStorage.get(chatId);
        fsm.setCurrentState(userState);

        String result = fsm.fire(
                getEvent(messageText),
                messageText,
                chatId
        );

        stateStorage.put(chatId, fsm.getCurrentState());
        System.out.println(chatId + ": " + fsm.getCurrentState().toString());
        return result;
    }

    Event getEvent(String messageText) {
        State currentState = fsm.getCurrentState();
        return switch (messageText) {
            case ("/start") -> Event.START;
            case ("/buyer") -> Event.BUYER;
            case ("/seller") -> Event.SELLER;
            case ("/back") -> Event.BACK;
            case ("/help") -> Event.HELP;
            case ("/listoforders") -> Event.ORDERS;
            case ("/menu") -> Event.MENU;
            case ("/cancel") -> Event.CANCEL_ORDER;
            case ("/duplicate") -> Event.DUPLICATE;
            case ("/order") -> Event.MAKE_ORDER;
            case ("/delete") -> Event.DELETE;
            case ("/cart") -> Event.CART;
            case ("/orders") -> Event.SELLER_ORDERS;
            case ("/nextStatus") -> Event.NEXT_STATUS;
            case ("/yes") -> Event.YES;
            case ("/no") -> Event.NO;
            case ("/rewrite") -> Event.REWRITE;
            default -> {
                try {
                    if(new State("review").equals(currentState)){
                        yield Event.TEXT;
                    }

                    int i = Integer.parseInt(messageText);
                    if(new State("rating").equals(currentState) &&
                                    (0 > i || i > 5)){
                        yield Event.HELP;
                    }
                    yield Event.INT;
                } catch (NumberFormatException e) {

                    yield Event.ERROR;
                }
            }
        };
    }
}
