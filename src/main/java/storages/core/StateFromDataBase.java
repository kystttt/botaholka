package storages.core;


import database.DataBase;
import database.DataBaseImpl;
import fsm.core.State;
import storages.api.StateStorage;

public class StateFromDataBase implements StateStorage {
    DataBase db = new DataBaseImpl();

    @Override
    public State get(Long id) {
        State result = db.getState(id);
        if(result == null){
            return new State("start");
        }
        return result;
    }

    @Override
    public void put(Long id, State newState) {
        int response = db.setState(id, newState);
        if(response == 0){
            System.out.println("Ошибка сохранения в ДБ");
        }
    }
}
