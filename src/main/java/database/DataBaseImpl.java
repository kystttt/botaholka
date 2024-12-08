package database;

import fsm.core.State;

import java.sql.*;

public class DataBaseImpl implements DataBase{
    private Connection connection;
    final String url = "jdbc:postgresql://localhost:5432/botaholka";
    final String user = "postgres";
    final String password = "1234";
    private final String tableName;

    public DataBaseImpl(){
        tableName = "users";
    }

    DataBaseImpl(String tableName){
        this.tableName = tableName;
    }

    int executeUpdate(String query) {
        try{
            connection.createStatement().executeUpdate(query);
            return 1;
        } catch (SQLException e){
            System.out.println(e);
            return 0;
        }
    }

    ResultSet executeQuery(String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

    int getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    int closeConnection(){
        try {
            connection.close();
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public State getState(Long chatId) {
        int connectionResponse = getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return null;
        }

        String query = "select state " +
                "from " + tableName +
                " where chat_id = " + chatId + ";";
        try{
            ResultSet resultSet = executeQuery(query);
            resultSet.next();
            String result = resultSet.getString("state");
            closeConnection();
            return new State(result);
        } catch (SQLException e){

            query = "insert into " + tableName +
                    "(chat_id, state) values (" +
                    chatId + ", 'start');";
            executeUpdate(query);
            closeConnection();
            return new State("start");
        }
    }

    @Override
    public int setState(Long chatId, State newState) {
        int connectionResponse = getConnection();
        if(connectionResponse != 1){
            System.out.println("не установил connection");
            return 0;
        }

        String query = "update " + tableName +
                " set state = '" + newState.name() + "'" +
                " where chat_id = " + chatId + ";";

        int response = executeUpdate(query);
        closeConnection();
        return response;
    }
}
