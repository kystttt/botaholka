/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {

    private ListOfOrders listOfOrders;

    private String output_message = "";

    public TextHandler(ListOfOrders listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    /**
     * Команда /start в боте
     */
    private void commandStart() {
        output_message = Constants.START_CONST;
    }

    /**
     * Команда /help в боте
     */
    private void commandHelp() {
        output_message = Constants.HELP_CONST;;
    }

    /**
     * Метод при вызове команды, которой нет у бота
     */
    private void commandWrongTypoWord() {
        output_message = Constants.ERROR_COMMAND;;
    }

    /**
     * геттер для output_message
     *
     * @return возвращает output_message
     */
    public String getOutputMassage(String message_text, Long chat_id) {
        logic(message_text, chat_id);
        return output_message;
    }

    /**
     * Реализует логику бота
     *
     * @param message_text переменная с текстом сообщения пользователя
     */
    private void logic(String message_text, Long chat_id) {

        switch (message_text) {
            case ("/help"):
                commandHelp();
                break;

            case ("/start"):
                commandStart();
                break;

            case ("/listoforders"):
                commandListOfOrders(chat_id);
                break;

            default:
                //Это будет исправлено вместе с задачей на контекст диалога
                if (message_text.startsWith("/duplicate-")) {
                    commandDuplicate(message_text);
                } else if (message_text.startsWith("/cancel-")) {
                    commandCancelOrder(message_text);
                } else {
                    commandWrongTypoWord();
                }
                break;

        }
    }

    /**
     * Повторяет заказ по его id
     */
    private void commandDuplicate(String messageText) {
        String msgNumber = messageText.split("-")[1];

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (msgNumber.equals(Long.toString(
                    listOfOrders.getHashMap().get(key).getChatId()))) {
                listOfOrders.putOrder(listOfOrders.getHashMap().get(key));
                output_message = "Заказ №" + listOfOrders.getHashMap().get(key).getChatId() + " продублирован ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", msgNumber);
    }

    /**
     * Удаляет заказ по его id
     */
    private void commandCancelOrder(String messageText) {
        String msgNumber = messageText.split("-")[1];

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (msgNumber.equals(Long.toString(
                    listOfOrders.getHashMap().get(key).getChatId()))) {
                Long deletedId = listOfOrders.getHashMap().get(key).getChatId();
                listOfOrders.removeById(listOfOrders.getHashMap().get(key).getOrder_id());
                output_message = "Заказ №" + deletedId + " удалён ";
                return;
            }
        }
        output_message = String.format("Заказ с №%s не найден", msgNumber);
    }


    /**
     * Метод для показа текущих заказов клиента по его chat_id
     *
     * @param chat_id номер чата
     */
    private void commandListOfOrders(Long chat_id) {
        boolean atLeastOnce = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (Integer key : listOfOrders.getHashMap().keySet()) {
            if (chat_id.equals(listOfOrders.getHashMap().get(key).getChatId())) {
                stringBuilder.append(listOfOrders.getHashMap().get(key).formMessageForClient());
                stringBuilder.append("\n");
                atLeastOnce = true;
            }
        }

        output_message = "Ваши заказы:\n";
        output_message += stringBuilder.toString();
        output_message += Constants.FUNCS_FOR_LIST_OF_ORDERS_BUYER;;
        if (!atLeastOnce) {
            output_message = "У вас нету действительных заказов";
        }
    }
}
