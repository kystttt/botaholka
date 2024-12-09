package database;

import fsm.core.State;
import utils.review.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDataBase implements DataBase<Review>{
    DB db;

    public ReviewDataBase(){
        db = new DB("reviews");
    }

    ReviewDataBase(String tableName){
        db = new DB(tableName);
    }

    @Override
    public Review get(Long chatId) {
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return null;
        }
        String query = "select * " +
                "from " + db.getTableName() +
                //TODO make offset 5
                ";";
//        try{
//            ResultSet resultSet = db.executeQuery(query);
//            resultSet.next();
//            String text = resultSet.getString("text");
//            int rating = resultSet.getInt("rating_5");
//            db.closeConnection();
//            return new Review(rating, text);
//        } catch (SQLException e){
//
//            query = "insert into " + db.getTableName() +
//                    "(chat_id, state) values (" +
//                    chatId + ", 'start');";
//            db.executeUpdate(query);
//            db.closeConnection();
//            return new State("start");
//        }
        return null;
    }

    @Override
    public int set(Long chatId, Review review) {
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return 0;
        }

        String query = "insert into " + db.getTableName() +
                "(chat_id,rating_5,text) values ( '" + chatId + "', '"
                + review.getRating() + "', '" +
                review.getText() + "');";

        int response = db.executeUpdate(query);
        db.closeConnection();
        return response;
    }
}
