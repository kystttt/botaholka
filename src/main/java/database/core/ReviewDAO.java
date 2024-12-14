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
     * из таблицы отзывов
     *
     * @param chatId id пользователя для поиска сдвига
     * @return список из (максимум) 5 отзывов,
     * null - если произошла ошибка с БД
     */
    public List<Review> get(Long chatId) {
        // TODO(На Лёне) Здесь надо исправить чтобы офсет не смещался,
        // когда уже закончился список отзывов
        int ofset = getOffset(chatId);
        List<Review> resultList = getReviews(ofset);
        if (resultList == null) {
            return null;
        }
        updateOffset(chatId, ofset);
        return resultList;
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
            return db.executeQuery("select *" +
                    "from users where chat_id = " + chatId + ";").getInt("ofset");
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
     * Обновляет ofset для пользователя по его id
     *
     * @param chatId id пользователя
     * @param ofset  Сдвиг для пользователя
     */
    private void updateOffset(Long chatId, int ofset) {
        try {
            db.executeUpdate("update users set ofset = " + ofset + " + 5 where chat_id = " + chatId + ";");
        } catch (SQLException e) {
            System.out.println("Ошибка сохранений данных(сдвига) в таблицу пользователей\n" + e);
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
