package urfu;

import java.util.Objects;

/**
 * Класс, методы которого обрабатывают текст
 */
public class TextHandler {

    private final String START_CONST = """
                                Добро пожаловать в бота
                                """;
    private final String ERROR_BRANCH = """
            В качестве кого вы бы хотели продолжить?
            /seller - продавец
            /buyer - покупатель
            """;
    private final String HELP_CONST = """
            Этот бот возвращает отправленное сообщение,
            Список команд:
            /help - Навигация по командам бота
            /start - приветствие пользователя
            
            /listoforders - просмотр текущих заказов
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;
    private final String ECHO_CONST = "Вы ввели: ";

    private final String FUNCS_FOR_LIST_OF_ORDERS_BUYER = """
            Ваши функции:
            /duplicate-{“Номер заказа”} - повторить заказ
            /cancel-{“Номер заказа”} - отменить заказ
            """;
    private final String ERROR_CMD = "Введите корректную команду, для списка всех команд - /help";
    private String output_message = "";
    /**
     * Метод, который работает с текстом
     */

    private void commandEcho(String str){
        output_message = ECHO_CONST + str;
    }

    /**
     * Команда /start в боте
     */
    private void commandStart(){
        output_message = START_CONST;
    }

    /**
     * Команда /help в боте
     */
     private void commandHelp(){
        output_message = HELP_CONST;
     }

    /**
     * Метод при вызове команды, которой нет у бота
     */
    private void commandWrongTypoWord(){
        output_message = ERROR_CMD;
    }

    /**
     * Метод для показа текущих заказов клиента по кго chat_id
     * @param chat_id номер чата
     */
     public void commandListOfOrders(Long chat_id){
         output_message = "Ваши заказы:\n";
         int i = 1;
         ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
         for(Integer key : listOfOrders.getHashMap().keySet()){
             if(Objects.equals(
                     listOfOrders.getHashMap().get(key).getChatId(),
                     chat_id)){
                 output_message += listOfOrders.getHashMap().get(key).formMessageForClient();
                 output_message += "\n";
             }
         }
         output_message += FUNCS_FOR_LIST_OF_ORDERS_BUYER;

     }

    /**
     * геттер для output_message
     * @return возвращает output_message
     */
    public String getOutputMassage(){
         return output_message;
     }

    /**
     * Реализует логику бота
     * @param message_text переменная с текстом сообщения пользователя
     */
    public void logic(String message_text, Long chat_id) {
//        Для задачи с контекстом диалога

//        UsersState usersState = UsersState.INSTANCE;
//        if(usersState.hasUser(chat_id)){
//            usersState.addUser(chat_id);
//            System.out.println("User created");
//        }
//        String branch = usersState.getBranch(chat_id);
//        if(branch.isEmpty()){
//            switch(message_text){
//                case("/seller"):
//                    usersState.setBranch(chat_id, "Seller");
//                    break;
//                case("/buyer"):
//                    usersState.setBranch(chat_id, "Buyer");
//                    break;
//                default:
//                    output_message = constants.ERROR_BRANCH;
//                    break;
//            }
//        }
//        if(branch.equals("Seller")){
//            //Task3
//            output_message = "Task3";
//        }
//        else if(branch.equals("Buyer"){
//
//        }

        switch (message_text) {
            case ("/help"):
                commandHelp();
                break;

            case ("/start"):
                commandStart();
                break;

            case("/listoforders"):
                commandListOfOrders(chat_id);
                break;

            default:
                //Это будет исправлено вместе с задачей на контекст диалога
                if (message_text.startsWith("/duplicate-")) {
                    commandDuplicate(message_text);
                }
                else if(message_text.startsWith("/delete-")){
                    commandDeleteOrder(message_text);
                }
                else {
                    commandWrongTypoWord();
                }
                break;

        }
    }

    /**
     * Удаляет заказ по его id
     */
    private void commandDuplicate(String messageText) {
        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        String msgNumber = messageText.split("-")[1];
        for(Integer key : listOfOrders.getHashMap().keySet()) {
            if(msgNumber.equals(Long.toString(
                    listOfOrders.getHashMap().get(key).getChatId()))){
                listOfOrders.putOrder(listOfOrders.getHashMap().get(key));
                output_message = "Заказ №" + listOfOrders.getHashMap().get(key).getChatId() + " продублирован ";
                break;
            }
        }

    }

    /**
     * Повторяет заказ по его id
     */
    private void commandDeleteOrder(String messageText) {
        ListOfOrders listOfOrders = ListOfOrders.INSTANCE;
        String msgNumber = messageText.split("-")[1];
        for(Integer key : listOfOrders.getHashMap().keySet()) {
            if(msgNumber.equals( Long.toString(listOfOrders.getHashMap().get(key).getChatId()))){
                Long deltedId = listOfOrders.getHashMap().get(key).getChatId();
                listOfOrders.removeById(listOfOrders.getHashMap().get(key).getOrder_id());
                output_message = "Заказ №" + deltedId + " удалён ";
                break;
            }
        }

    }
}
