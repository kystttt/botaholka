package logic;

import fsm.cfg.Event;
import fsm.cfg.States;
import fsm.core.FiniteStateMachine;
import fsm.core.State;
import storages.api.StateStorage;
import storages.core.StateStorageImpl;


/**
 * Логика бота для обработки сообщений
 */
public class BotLogic {
    FiniteStateMachine fsm;
    StateStorage stateStorage = new StateStorageImpl();

    public BotLogic(){
        fsm = new FiniteStateMachine();
    }

    /**
     * Обработка текста
     * @param messageText Сообщение пользователя
     * @param chatId Id пользователя
     * @return Строку с ответом бота
     */
    public String processMessage(String messageText, Long chatId){
        State userState = stateStorage.get(chatId);
        fsm.setCurrentState(userState);

        String result =  fsm.fire(
                switch(messageText){
                    case ("/start") -> Event.START;
                    case ("/buyer") -> Event.BUYER;
//                    case ("/seller") -> Event.SELLER;
                    case("/back") -> Event.BACK;
                    case("/help") -> Event.HELP;
                    case("/listoforders") -> Event.ORDERS;
                    case("/menu") -> Event.MENU;
                    case("/cancel") -> Event.CANCEL_ORDER;
                    case("/duplicate") -> Event.DUPLICATE;
                    case("/order") -> Event.MAKE_ORDER;
                    case("/delete") -> Event.DELETE;
                    default -> {
                        try{
                            int i = Integer.parseInt(messageText);
                            yield Event.INT;
                        }
                        catch(NumberFormatException e){
                            yield Event.ERROR;
                        }
                    }
                },
                messageText,
                chatId
        );
        stateStorage.put(chatId, fsm.getCurrentState());
        System.out.println(chatId + ": " + fsm.getCurrentState().toString());
        return result;
    }
}
