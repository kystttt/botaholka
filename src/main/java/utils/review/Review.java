package utils.review;

import java.util.Objects;

/**
 * Отзыв
 */
public class Review {
    private String text;
    private int rating;

    public Review(int rating, String text){
        this.rating = rating;
        this.text = text;
    }

    public Review(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rating == review.rating && Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "text='" + text + '\'' +
                ", rating=" + rating +
                '}';
    }
}
