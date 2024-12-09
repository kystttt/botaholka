package database;

import fsm.core.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.review.Review;

public class ReviewDataBaseTest {
    ReviewDataBase reviewDataBase = new ReviewDataBase("test");

    /**
     * Тест на корректное присваивание полей отзыва в базе данных
     */
    @Test
    void setTest(){
        reviewDataBase.createCustomerTable();
        int queryResponse = reviewDataBase.set(312L, new Review(5, "test1"));
        Assertions.assertEquals(1, queryResponse);
        Review review = reviewDataBase.get(312L);
        Assertions.assertEquals(5, review.getRating());
        Assertions.assertEquals("test1", review.getText());
    }
}
