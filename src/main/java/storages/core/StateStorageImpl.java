package storages.core;

import fsm.cfg.States;
import fsm.core.State;
import storages.api.StateStorage;

import java.util.HashMap;
import java.util.Map;

public class StateStorageImpl implements StateStorage {
    Map<Long, State> items;

    public StateStorageImpl(){
        items = new HashMap<>();
    }

    @Override
    public State get(Long id) {
        return items.getOrDefault(id, new States().start);
    }

    @Override
    public void put(Long id, State newState) {
        items.put(id, newState);
    }
}
