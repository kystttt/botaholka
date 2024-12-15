package storages.core;


import database.core.DB;
import database.core.StateDAO;
import fsm.core.State;
import storages.api.StateStorage;
import utils.Constants;

public class StateFromDataBase implements StateStorage {
    private final DB db = new DB(
            "jdbc:postgresql://localhost:5432/botaholka",
            "postgres",
            System.getenv("DB_PASSWORD"));
    StateDAO stateDAO = new StateDAO(db);

    @Override
    public State get(Long id) {
        State result = stateDAO.getState(id);
        if (result == null) {
            stateDAO.addUser(id);
            return new State("start");
        }
        return result;
    }

    @Override
    public void put(Long id, State newState) {
        boolean response = stateDAO.updateState(id, newState);
        if (!response) {
            System.out.println("Ошибка сохранения в ДБ");
        }
    }
}
