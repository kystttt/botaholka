package fsm.core;

/**
 * Выполняет действие для {@link fsm.cfg.Event}
 */
@FunctionalInterface
public interface EventHandler{
    /**
     * Обрабатывает текст
     * @param messageText Текст пользователя
     * @param chatId ID пользователя
     * @return Текст, который увидит пользователь
     */
    String handleEvent(String messageText, long chatId);
}
