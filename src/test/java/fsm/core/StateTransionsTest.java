//package fsm.core;
//import fsm.cfg.Event;
//import fsm.cfg.States;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Проверка всех переходов между всеми состояниями
// */
//public class StateTransionsTest {
//    FiniteStateMachine fsm;
//    States states = new States();
//
//    String tmplMessageText = "1";
//    Long tmplChatId = 1L;
//
//    List<Event> events = new ArrayList<>(List.of(Event.values()));
//
//    void initFSM(State initialState){
//        fsm = new FiniteStateMachine(initialState);
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния START
//     */
//    @Test
//    public void StateStartTest() {
//        State expected_state;
//        for (Event event : events) {
//            initFSM(states.start);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//            if (event == Event.BUYER) {
//                expected_state = states.buyer;
//            }
//            else if (event == Event.SELLER) {
//                expected_state = states.seller;
//            }
//            else {
//                expected_state = states.start;
//            }
//
//            Assertions.assertEquals(expected_state, fsm.getCurrentState());
//        }
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния BUYER
//     */
//    @Test
//    public void StateBuyerTest() {
//        State expected_state;
//        for (Event event : events) {
//            initFSM(states.buyer);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if (event == Event.MENU) {
//                expected_state = states.menu;
//            }
//            else if(event == Event.BACK){
//                expected_state = states.start;
//            }
//            else if(event == Event.ORDERS){
//                expected_state = states.listOfOrders;
//            }
//            else {
//                expected_state = states.buyer;
//            }
//
//            Assertions.assertEquals(expected_state, fsm.getCurrentState());
//        }
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния ORDERS
//     */
//    @Test
//    public void StateOrdersTest() {
//        State expected_state;
//        for (Event event : events) {
//            initFSM(states.listOfOrders);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if (event == Event.DUPLICATE) {
//                expected_state = states.duplicate;
//            }
//            else if(event == Event.BACK){
//                expected_state = states.buyer;
//            }
//            else if(event == Event.CANCEL_ORDER){
//                expected_state = states.cancel;
//            }
//            else {
//                expected_state = states.listOfOrders;
//            }
//
//            Assertions.assertEquals(expected_state, fsm.getCurrentState());
//        }
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния DUPLICATE
//     */
//    @Test
//    public void StateDuplicateTest() {
//        State expected_state;
//        for (Event event : events) {
//            initFSM(states.duplicate);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if (event == Event.INT || event == Event.BACK) {
//                expected_state = states.listOfOrders;
//            }
//            else {
//                expected_state = states.duplicate;
//            }
//
//            Assertions.assertEquals(expected_state, fsm.getCurrentState());
//        }
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния CANCEL
//     */
//    @Test
//    public void StateCancelTest() {
//        // очень странно, потому что IDEA не даёт не инициализировать
//        State expectedState = states.cancel;
//        for (Event event : events) {
//            initFSM(states.cancel);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if (event == Event.INT || event == Event.BACK) {
//                expectedState = states.listOfOrders;
//            }
//            else {
//                expectedState = states.cancel;
//            }
//        }
//
//        Assertions.assertEquals(expectedState, fsm.getCurrentState());
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния MENU
//     */
//    @Test
//    public void StateMenuTest() {
//        State expectedState;
//        for (Event event : events) {
//            initFSM(states.menu);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if (event == Event.DELETE) {
//                expectedState = states.delete;
//            }
//            else if(event == Event.BACK || event == Event.MAKE_ORDER){
//                expectedState = states.buyer;
//            }
//            else {
//                expectedState = states.menu;
//            }
//
//            Assertions.assertEquals(expectedState, fsm.getCurrentState());
//        }
//    }
//
//    /**
//     * Проверка перехода во всевозможные состояния из состояния DELETE
//     */
//    @Test
//    public void StateDeleteTest() {
//        State expectedState;
//        for (Event event : events) {
//            initFSM(states.delete);
//            fsm.fire(event, tmplMessageText, tmplChatId);
//
//            if(event == Event.BACK || event == Event.INT){
//                expectedState = states.menu;
//            }
//            else {
//                expectedState = states.delete;
//            }
//
//            Assertions.assertEquals(expectedState, fsm.getCurrentState());
//        }
//    }
//}
