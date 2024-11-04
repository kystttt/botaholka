package fsm.core;

public interface EventHandler{
    String handleEvent(String messageText, long chatId);
}
