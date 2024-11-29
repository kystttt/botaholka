package logic;

import fsm.cfg.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventTest {
    BotLogic logic;

    public EventTest(){
        logic = new BotLogic();
    }

    @Test
    public void getEventTest(){
        Event actual = logic.getEvent("/start");
        Assertions.assertEquals(Event.START, actual);

        actual = logic.getEvent("/buyer");
        Assertions.assertEquals(Event.BUYER, actual);

        actual = logic.getEvent("/seller");
        Assertions.assertEquals(Event.SELLER, actual);

        actual = logic.getEvent("/back");
        Assertions.assertEquals(Event.BACK, actual);

        actual = logic.getEvent("/help");
        Assertions.assertEquals(Event.HELP, actual);

        actual = logic.getEvent("/listoforders");
        Assertions.assertEquals(Event.ORDERS, actual);

        actual = logic.getEvent("/menu");
        Assertions.assertEquals(Event.MENU, actual);

        actual = logic.getEvent("/cancel");
        Assertions.assertEquals(Event.CANCEL_ORDER, actual);

        actual = logic.getEvent("/duplicate");
        Assertions.assertEquals(Event.DUPLICATE, actual);

        actual = logic.getEvent("/order");
        Assertions.assertEquals(Event.MAKE_ORDER, actual);

        actual = logic.getEvent("/delete");
        Assertions.assertEquals(Event.DELETE, actual);

        actual = logic.getEvent("/cart");
        Assertions.assertEquals(Event.CART, actual);

        actual = logic.getEvent("/orders");
        Assertions.assertEquals(Event.SELLER_ORDERS, actual);

        actual = logic.getEvent("/nextStatus");
        Assertions.assertEquals(Event.NEXT_STATUS, actual);

        actual = logic.getEvent("1");
        Assertions.assertEquals(Event.INT, actual);

        actual = logic.getEvent("fsdfsfsdfs");
        Assertions.assertEquals(Event.ERROR, actual);
    }


}
