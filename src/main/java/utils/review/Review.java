package utils.review;

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
}
