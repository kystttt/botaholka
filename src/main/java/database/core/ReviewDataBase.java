package database.core;

import database.api.DataBase;
import utils.review.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDataBase implements DataBase<Review> {
    DB db;
    private final String tableName;

    public ReviewDataBase(String tableName){
        this.tableName = tableName;
        db = new DB(tableName);
    }

    /**
     * Возвращает по 5 отзывов
     */
    public List<Review> getReviews(long chatId){
        int ofset;
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return null;
        }

        try{
            ofset = db.executeQuery("select *" +
                    "from users where chat_id = " + chatId + ";").getInt("ofset");
        } catch (SQLException e){
            ofset = 0;
        }
        String query = "select * " +
                "from " + tableName +
                " limit 5 offset " + ofset + ";";
        try{
            ResultSet resultSet = db.executeQuery(query);
            List<Review> result = new ArrayList<>();
            int index = 0;
            while(resultSet.next() && index < 5){
                int rating = resultSet.getInt("rating_5");
                String text = resultSet.getString("text");
                result.add(new Review(rating, text));
                index++;
            }
            db.executeUpdate("update users set ofset = ofset + 5 where chat_id = " + chatId + ";");
            return result;
        }catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Review> get(Long chatId) {
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return null;
        }
        String query = "select * " +
                "from " + tableName +
                " where chat_id = " + chatId + ";";
        try{
            ResultSet resultSet = db.executeQuery(query);
            resultSet.next();
            String text = resultSet.getString("text");
            int rating = resultSet.getInt("rating_5");
            db.closeConnection();
            return List.of(new Review(rating, text));
        } catch (SQLException e){
            return List.of(new Review(1, "1"));
        }
    }

    @Override
    public int set(Long chatId, Review review) {
        int connectionResponse = db.getConnection();
        if(connectionResponse == 0){
            System.out.println("не установил connection");
            return 0;
        }

        String query = "insert into " + tableName +
                "(chat_id,rating_5,text) values ( '" + chatId + "', '"
                + review.getRating() + "', '" +
                review.getText() + "');";

        int response = db.executeUpdate(query);
        db.closeConnection();
        return response;
    }

    @Override
    public void createTestTable() {
        db.createReviewTestTable();
    }
}
