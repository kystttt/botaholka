package database;

import fsm.core.State;

import java.util.Optional;

public interface DataBase {

    /**
     * Возвращает состояние пользователя по его id
     * или null, если не смог подучить нужный столбец
     */
    State getState(Long chatId);

    /**
     * Обновляет состояние для пользователя
     * @param chatId id пользователя
     * @param newState Новое состояние
     * @return обновление состояния прошло (1) - успешно,
     * (0) - не успешно
     */
    int setState(Long chatId, State newState);
}
