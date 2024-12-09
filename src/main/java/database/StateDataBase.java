package database;

import fsm.core.State;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Реализация базы данных для получения и присваивания состояний пользователя
 */
public class StateDataBase implements DataBase<State>{
    DB db;

    public StateDataBase(){
        db = new DB("users");
    }

    StateDataBase(String tableName){
        db = new DB(tableName);
    }

    @Override
    public State get(Long chatId) {
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
            return new State(result);
        } catch (SQLException e){

            query = "insert into " + db.getTableName() +
                    "(chat_id, state) values (" +
                    chatId + ", 'start');";
            db.executeUpdate(query);
            db.closeConnection();
            return new State("start");
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

    public void createCustomerTable() {
        db.createCustomerTable();
    }
}
