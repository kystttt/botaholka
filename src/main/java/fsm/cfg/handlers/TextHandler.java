package fsm.cfg.handlers;

import database.core.DB;
import database.core.HistoryDAO;
import database.core.ReviewDAO;
import menu.*;
import utils.order.FormOrderMessage;
import storages.api.Cart;
import storages.core.ListCart;
import storages.core.ListOfOrders;
import utils.order.Order;
import storages.api.Orders;
import utils.Constants;
import utils.review.Review;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {
    final Orders listOfOrders;
    final Cart cart;
    Menu menu;
    private final DB db = new DB(
            Constants.DB_URL,
            Constants.DB_USERNAME,
            System.getenv("DB_PASSWORD")
    );
    private final Map<Long, Review> reviews = new HashMap<>();
    private final ReviewDAO reviewTable = new ReviewDAO(db);
    private final HistoryDAO historyTable = new HistoryDAO(db);

    public TextHandler(String menuFileName) {
        listOfOrders = new ListOfOrders();
        cart = new ListCart();
        menu = new MenuImpl(menuFileName);
    }

    public TextHandler(Menu menu) {
        listOfOrders = new ListOfOrders();
        cart = new ListCart();
        this.menu = menu;
    }

    /**
     *  Создает заказ из того, что в корзине
     * @param chat_id Id того пользователя для кого создается заказ
     */
    public String makeOrder(Long chat_id){
        Order order = new Order(chat_id);
        if (cart.size() == 0){
            return Constants.CART_EMPTY_CONST;
        }

        for(int i = 0; i < cart.size(); i++){
            String[] parts = cart.get(i).split("[-. ]+");
            order.addToArr(parts[0]);
        }

        listOfOrders.put(order);
        cart.clear();
        return Constants.MADE_ORDER_CONST;
    }

    /**
     * Повторяет заказ по его id
     */
    public String duplicate(String messageTxtIndex, long chatId) {
        String output_message;
        for (Order order : listOfOrders.getOrders()) {
            if (
                    messageTxtIndex.equals(Long.toString(order.getId())) &&
                            order.getChatId() == chatId

            ) {
                listOfOrders.put(new Order(order));
                output_message = "Заказ №" + order.getId() + " продублирован ";
                return output_message;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
        return output_message;
    }

    /**
     * Метод, который добавляет по названию товар в корзину
     */
    public String addToCart(String msgTxt, long chatId) {
        String dishName = menu.getName(Integer.parseInt(msgTxt));
        String output_message;
        if (menu.getCost(dishName) != -1) {
            String dishDetails =  dishName + " - " + menu.getCost(dishName) + " рублей"; // Получаем детали блюда
            cart.add(dishDetails);
            output_message = Constants.DISH_ADDED_CONST + dishDetails +
                    Constants.YOUR_CART_CONST;
        } else {
            output_message = Constants.ERROR_UNDEFINED_NUMB_CONST;
        }
        return output_message;
    }

    /**
     * Метод, который показывает корзину покупателя.
     */
    public String viewCart(Long chatId) {
        String output_message;
        if (cart.size() == 0) {
            output_message = Constants.CART_EMPTY_CONST;
            return output_message;
        }
        StringBuilder cartContents = new StringBuilder(Constants.YOUR_ORDER_CONST);

        for (int i = 0; i < cart.size(); i++) {
            cartContents
                    .append(i+1)
                    .append(". ")
                    .append(cart.get(i))
                    .append("\n");
        }

        output_message = cartContents.toString() + Constants.HELP_CLONE + "/back - вернуться в меню\n/delete - удалить из коризны";
        return output_message;
    }

    /**
     * Метод, который удаляет из корзины блюдо по индексу из корзины
     */
    public String deleteFromCart(String dishIndexStr, long chatId){
        try {
            int idx = Integer.parseInt(dishIndexStr) - 1;
            if (idx >= 0 && idx < cart.size()) {
                cart.remove(idx);
                return Constants.SUCCESS_DELETE_DISH_CONST;
            } else {
                return Constants.ERROR_INDEX_CONST;
            }
        } catch (NumberFormatException e) {
            return Constants.ERROR_TYPE_CONST;
        }
    }

    /**
     * Удаляет заказ по его id
     */
    public String cancelOrder(String messageTxtIndex, long chatId) {
        String output_message;
        for (Order order : listOfOrders.getOrders()) {
            if (
                    messageTxtIndex.equals(String.valueOf(order.getId())) &
                            chatId == order.getChatId()
            ) {
                listOfOrders.remove(order.getId());
                output_message = "Заказ №" + messageTxtIndex + " удалён ";
                return output_message;
            }
        }
        output_message = String.format("Заказ с №%s не найден", messageTxtIndex);
        return output_message;
    }

    /**
     * Метод, который вызывает меню(показывает, что есть в ассортименте)
     */
    public String menuCalling() {
        String output_message;
        StringBuilder menuBuilder = new StringBuilder(Constants.MENU_CONST);
        int index = 1;
        for (String name: menu.getFoodNames()){
            String stringCost = menu.getCost(name).toString();
            menuBuilder
                    .append(index)
                    .append(". ")
                    .append(name)
                    .append(" - ")
                    .append(stringCost)
                    .append(" рублей\n");
            index++;
        }
        menuBuilder.append(Constants.CHOOSE_CONST);
        output_message = menuBuilder.toString();
        return output_message;
    }

    /**
     * Метод для показа текущих заказов клиента по его chat_id
     *
     * @param chat_id номер чата
     */
    public String listOfOrders(Long chat_id) {
        String output_message;
        boolean atLeastOnce = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (Order order : listOfOrders.getOrders()) {
            if (chat_id.equals(order.getChatId())) {
                stringBuilder.append(new FormOrderMessage().forClient(order, menu));
                stringBuilder.append("\n");
                atLeastOnce = true;
            }
        }

        output_message = "Ваши заказы:\n";
        output_message += stringBuilder.toString();
        output_message += Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;
        if (!atLeastOnce) {
            output_message = Constants.NO_AVAILABLE_ORDERS + "\n\n" +
                    Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;
        }
        return output_message;
    }

    /**
     * Метод, который выводит заказы абсолютно всех пользователей
     */
    public String usersListOfOrders(){
        String output_message;
        StringBuilder stringBuilder = new StringBuilder();
        if (listOfOrders.size() != 0){
            for (Order order : listOfOrders.getOrders()) {
                stringBuilder.append(new FormOrderMessage().forSeller(order));
                stringBuilder.append("\n");
            }
            output_message = "Ваши заказы:\n";
            output_message += stringBuilder.toString() + "\n" + Constants.HELP_CLONE +
                    "/order №Заказа - просмотр и изменение конкретного заказа\n" +
                    "/back - вернуться назад";
        }
        else{
            output_message = "Список текущих заказов пуст\n/back - вернуться назад";
        }

        return output_message;
    }

    /**
     * Метод, который меняет статус заказа, по индексу заказаё
     */
    public String nextStatus(String dishIndexStr, long chatId){
        String output_message;
        int idx = Integer.parseInt(dishIndexStr);
        if (idx > listOfOrders.size()){
            output_message = "Такого заказа не существует\n";
            return output_message;
        }
        String tempOrderStatus;
        for (Order order : listOfOrders.getOrders()){
            if (idx == order.getId()){
                tempOrderStatus = order.getStatus();
                if (tempOrderStatus.equals("Выдан")){
                    output_message =  "Статус заказа не изменен\n";
                    return output_message;
                }
                int response = order.setStatus();
                if(response == 1){
                    listOfOrders.remove(order.getId());
                    historyTable.addOrder(chatId, order);
                }
            }
        }
        output_message = "Статус заказа изменён\n";
        return output_message;
    }

    public String sellerOrder(String messageTxtIndex, Long chatId){
        String output_message;
        int idx = Integer.parseInt(messageTxtIndex);
        StringBuilder stringBuilder = new StringBuilder();
        if (listOfOrders.size() != 0 && idx < listOfOrders.size() + 1){
            for (Order order : listOfOrders.getOrders()) {
                if (order.getId() == idx){
                    stringBuilder.append(new FormOrderMessage().forClient(order, menu));
                    stringBuilder.append("\n");
                }

            }
        }
        else{
            return "Ошибка!";
        }
        output_message = stringBuilder + "\n"  +  "/nextStatus - изменение статуса заказа на следующий\n" ;

        return output_message;
    }

    /**
     * Сохраняет текст отзыва
     */
    public String reviewText(String messageText, long chatId) {
        Review review = reviews.get(chatId);
        if(review == null){
            reviews.put(chatId, new Review(messageText));
        } else{
            review.setText(messageText);
            reviews.put(chatId, review);
        }
        return "";
    }

    /**
     * Сохраняет оценку отзыва
     */
    public String rating(long chatId, String messageText) {
        Review review = reviews.get(chatId);
        if(review == null){
            System.out.println("Технически этого случая быть не должно, " +
                    "потому что этот метод вызывается всегда после reviewText," +
                    "а там стопроцентная инициализация ревью");
            return "Ты сломал систему, молодец";
        } else{
            review.setRating(Integer.parseInt(messageText));
            reviews.put(chatId, review);
        }
        return "Ваш отзыв:\n" +
                review.getRating() + "/5\n" +
                review.getText() +
                Constants.END_REVIEW;
    }

    /**
     * Записывает текст и оценку отзыва в бд для пользователя по его id
     */
    public String insertReview(long chatId) {
        boolean response = reviewTable.addReview(chatId, reviews.get(chatId));
        reviews.remove(chatId);
        if (response){
            return "Отзыв успешно добавлен\n";
        } else {
            return "К сожалению ваш отзыв не добавлен по техническим причинам\n";
        }
    }

    /**
     * Показывает по 5 отзывов
     */
    public String allReviews(long chatId) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Review review : reviewTable.getReviews(chatId)){
            stringBuilder
                    .append(review.getRating())
                    .append("\n")
                    .append(review.getText())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    public String history(long chatId) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Order order : historyTable.getOrders(chatId)){
            stringBuilder.append(new FormOrderMessage().forHistory(order, menu));
        }
        return stringBuilder.toString();
    }
}
