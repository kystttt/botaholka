package database;

import database.api.DataBase;
import database.core.ReviewDataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.review.Review;

import java.util.List;

public class ReviewDataBaseTest {
    DataBase<Review> reviewDataBase = new ReviewDataBase("test");

    /**
     * Тест на корректное присваивание полей отзыва в базе данных
     */
    @Test
    void apiTest(){
        reviewDataBase.createTestTable();
        int queryResponse = reviewDataBase.set(312L, new Review(5, "test1"));
        Assertions.assertEquals(1, queryResponse);
        List<Review> actual = reviewDataBase.get(312L);
        Review review = actual.getLast();
        Assertions.assertEquals(5, review.getRating());
        Assertions.assertEquals("test1", review.getText());

        List<Review> expected = List.of(new Review(4, "test"),
                new Review(5, "test1"));

        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(expected, actual);
    }
}
