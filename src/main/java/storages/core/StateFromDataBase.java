package storages.core;


import database.DataBase;
import database.StateDataBase;
import fsm.core.State;
import storages.api.StateStorage;

public class StateFromDataBase implements StateStorage {
    DataBase<State> db = new StateDataBase();

    @Override
    public State get(Long id) {
        State result = db.get(id);
        if(result == null){
            return new State("start");
        }
        return result;
    }

    @Override
    public void put(Long id, State newState) {
        int response = db.set(id, newState);
        if(response == 0){
            System.out.println("Ошибка сохранения в ДБ");
        }
    }
}
