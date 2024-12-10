package database.core;

import database.api.DataBase;
import fsm.core.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Реализация базы данных для получения и присваивания состояний пользователя
 */
public class StateDataBase implements DataBase<State> {
    DB db;
    private final String tableName;

    public StateDataBase(String tableName){
        this.tableName = tableName;
        db = new DB(tableName);
    }

    @Override
    public List<State> get(Long chatId) {
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return null;
        }

        String query = "select state " +
                "from " + db.getTableName() +
                " where chat_id = " + chatId + ";";
        try{
            ResultSet resultSet = db.executeQuery(query);
            resultSet.next();
            String result = resultSet.getString("state");
            db.closeConnection();
            return List.of(new State(result));
        } catch (SQLException e){

            query = "insert into " + db.getTableName() +
                    "(chat_id, state) values (" +
                    chatId + ", 'start');";
            db.executeUpdate(query);
            db.closeConnection();
            return List.of(new State("start"));
        }
    }

    @Override
    public int set(Long chatId, State newState) {
        int connectionResponse = db.getConnection();
        if(connectionResponse != 1){
            System.out.println("не установил connection");
            return 0;
        }

        String query = "update " + db.getTableName() +
                " set state = '" + newState.name() + "'" +
                " where chat_id = " + chatId + ";";

        int response = db.executeUpdate(query);
        db.closeConnection();
        return response;
    }

    @Override
    public void createTestTable() {
        db.createStateTestTable();
    }
}
