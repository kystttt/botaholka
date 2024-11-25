package storages.api;

import fsm.core.State;

/**
 * Хранит информацию о текущем {@link fsm.core.State} каждого пользователя
 */
public interface StateStorage {
    /**
     * Возвращает {@link fsm.core.State} по id пользователя,
     * если такого id нет, возвращает {@link fsm.core.State} start
     */
    State get(Long id);

    /**
     * Обновляет {@link fsm.core.State} для пользователя,
     * если пользователя с данным id нет, то создает его:
     * Key-id, Value-{@link fsm.core.State}
     * @param id id пользователя
     * @param newState Новый State
     */
    void put(Long id, State newState);
}
