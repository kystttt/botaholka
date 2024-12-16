package database.core;

import utils.review.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Управление таблицей отзывов
 */
public class ReviewDAO {
    private final DB db;
    private final String tableName = "reviews";

    public ReviewDAO(DB db) {
        this.db = db;
    }

    /**
     * Получает список отзывов для пользователя по его id
     * из таблицы отзывов.
     *
     * @param chatId id пользователя для поиска сдвига
     * @return список из (максимум) 5 отзывов, пустой список - если список исчерпан,
     * null - если произошла ошибка с БД
     */
    public List<Review> getReviews(Long chatId) {
        int offset = getOffset(chatId);
        int totalReviews = getTotalReviewsCount();

        if (totalReviews == -1) {
            return null;
        }

        if (offset >= totalReviews) {
            System.out.println("Все отзывы уже просмотрены.");
            return List.of();
        }

        List<Review> resultList = getReviews(offset);
        if (resultList == null) {
            return null;
        }

        if (resultList.size() > 5) {
            updateOffset(chatId, offset);
        }

        return resultList;
    }

    /**
     * Возвращает общее количество записей в таблице отзывов.
     *
     * @return количество записей или -1 в случае ошибки.
     */
    private int getTotalReviewsCount() {
        try {
            ResultSet resultSet = db.executeQuery("SELECT COUNT(*) AS total FROM " + tableName + ";");
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете количества отзывов: " + e);
        }
        return -1;
    }



    /**
     * Возвращает сдвиг для пользователя по его id
     *
     * @param chatId id пользователя
     * @return 0 - если у пользователя поле ofset пустое,
     * сдвиг - для пользователя по его id
     */
    private int getOffset(Long chatId) {
        try {
            ResultSet resultSet = db.executeQuery("select * " +
                    "from users where chat_id = " + chatId + ";");
            resultSet.next();
            return resultSet.getInt("ofset");
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Возвращает List с 5 отзывами, начиная с некоторого номера(ofset)
     * @param ofset Номер начиная с которого возвращаются 5 отзывов
     * @return null - если возникла ошибка обращения к БД,
     * List - если всё успешно
     */
    private List<Review> getReviews(int ofset) {

        String query = "select * " +
                "from " + tableName +
                " limit 5 offset " + ofset + ";";

        try {
            ResultSet resultSet = db.executeQuery(query);
            List<Review> result = new ArrayList<>();
            int index = 0;
            while (resultSet.next() && index < 5) {
                int rating = resultSet.getInt("rating_5");
                String text = resultSet.getString("text");
                result.add(new Review(rating, text));
                index++;
            }
            return result;
        } catch (SQLException e) {
            System.out.println("Не удалось получить данные из таблицы отзывов\n" + e);
            return null;
        }
    }

    /**
     * Обновляет ofset для пользователя по его id.
     *
     * @param chatId id пользователя
     * @param ofset  Текущий сдвиг пользователя
     */
    private void updateOffset(Long chatId, int ofset) {
        try {
            String query = "UPDATE users SET ofset = " + (ofset + 5) + " WHERE chat_id = " + chatId + ";";
            db.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения данных (сдвига) в таблицу пользователей\n" + e);
        }
    }

    /**
     * Добавляет в таблицу истории отзыва по id пользователя
     * @param chatId id пользователя
     * @param review Отзыв пользователя
     * @return (true) - отзыв успешно добавлен,
     * (false) - не удалось добавить отзыв в БД
     */
    public boolean addReview(Long chatId, Review review) {
        String query = "insert into " + tableName +
                "(chat_id,rating_5,text) values ( '" + chatId + "', '"
                + review.getRating() + "', '" +
                review.getText() + "');";

        try {
            db.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println("Не удалось вставить в таблицу отзывов строку chatId - Review\n" + e);
            return false;
        }
    }
}