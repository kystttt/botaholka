package database;

import database.core.DB;
import database.core.ReviewDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.review.Review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Тесты для ReviewDAO
 */
public class ReviewDAOTest {
    private final ReviewDAO reviewDAO;

    public ReviewDAOTest() {
        DB db = new DB("jdbc:h2:mem:test", "sa", "");
        reviewDAO = new ReviewDAO(db);
    }

    /**
     * Создает таблицу reviews
     */
    private void createReviewTestTable() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");

            String customerTableQuery = "CREATE TABLE reviews " +
                    "(id SERIAL primary key,chat_id INT, rating_5 INT, text TEXT); ";

            connection.createStatement().executeUpdate(customerTableQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу/вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Удаляет таблицы users, reviews
     */
    private void deleteTables() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            connection.createStatement().executeUpdate("drop table reviews;");
            connection.createStatement().executeUpdate("drop table users;");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицы\n" + e);
        }
    }

    /**
     * Вставляет строку в таблицу reviews(id, рейтинг, текст отзыва)
     * @param chatId id
     * @param rating рейтинг
     * @param text текст отзыва
     */
    private void insertInTable(Long chatId, int rating, String text) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");
            String customerEntryQuery = "INSERT INTO reviews(chat_id, rating_5, text) " +
                    "VALUES (" + chatId + ", " + rating + ", '" + text + "')";
            connection.createStatement().executeUpdate(customerEntryQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось вставить данные в таблицу\n" + e);
        }
    }

    /**
     * Создает таблицу users,
     * создаёт в ней пользователя(id, состояние="test", ofset=1)
     */
    private void createUsersTable(Long id) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:h2:mem:test", "sa", "");

            String customerTableQuery = "CREATE TABLE users " +
                    "(id SERIAL, chat_id INT primary key, state TEXT, ofset INT)";

            String customerEntryQuery = "INSERT INTO users(chat_id, state, ofset) " +
                    "VALUES (" + id + ", 'test', 1)";

            connection.createStatement().executeUpdate(customerTableQuery);
            connection.createStatement().executeUpdate(customerEntryQuery);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу/вставить данные в таблицу (users)\n" + e);
        }
    }

    /**
     * Тест для получения отзывов
     */
    @Test
    public void getReviewsTest() {
        createReviewTestTable();
        createUsersTable(123L);
        insertInTable(123L, 1, "test1");
        insertInTable(123L, 1, "test2");
        insertInTable(123L, 1, "test3");
        insertInTable(123L, 1, "test4");
        insertInTable(123L, 1, "test5");
        insertInTable(123L, 1, "test6");

        List<Review> actual = reviewDAO.getReviews(123L);
        List<Review> expected = List.of(
                new Review(1, "test2"),
                new Review(1, "test3"),
                new Review(1, "test4"),
                new Review(1, "test5"),
                new Review(1, "test6"));
        Assertions.assertEquals(expected, actual);

        actual = reviewDAO.getReviews(12L);
        expected = List.of(
                new Review(1, "test1"),
                new Review(1, "test2"),
                new Review(1, "test3"),
                new Review(1, "test4"),
                new Review(1, "test5"));
        Assertions.assertEquals(expected, actual);
        deleteTables();
    }

    /**
     * Тест для добавления
     */
    @Test
    public void addReviewTest(){
        createReviewTestTable();
        createUsersTable(123L);

        reviewDAO.addReview(12L, new Review(1, "test1"));

        List<Review> actual = reviewDAO.getReviews(12L);
        List<Review> expected = List.of(new Review(1, "test1"));

        Assertions.assertEquals(expected, actual);
        deleteTables();
    }
}
