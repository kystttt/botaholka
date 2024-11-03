package fsm.cfg;

import fsm.core.Transition;
import fsm.core.TransitionBuilder;

import java.util.Set;

public class Transitions {
    EventHandlers eventHandlers = new EventHandlers();
    States states = new States();

    //TODO Нужен ли EventHandler для обратных(BACK) переходов

    Transition buyer = new TransitionBuilder()
            .event(Event.BUYER)
            .eventHandler(eventHandlers.buyer)
            .startState(states.start)
            .endState(states.buyer)
            .build();

    Transition buyerBack = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.buyer)
            .endState(states.start)
            .build();

    Transition orders = new TransitionBuilder()
            .event(Event.ORDERS)
            .eventHandler(eventHandlers.listOfOrders)
            .startState(states.buyer)
            .endState(states.listOfOrders)
            .build();

    Transition ordersback = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.listOfOrders)
            .endState(states.buyer)
            .build();

    Transition cancel = new TransitionBuilder()
            .event(Event.CANCEL)
            .eventHandler(eventHandlers.cancel)
            .startState(states.listOfOrders)
            .endState(states.cancel)
            .build();

    Transition cancelBack = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.cancel)
            .endState(states.listOfOrders)
            .build();

    Transition cancelInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.cancelInt)
            .startState(states.cancel)
            .endState(states.listOfOrders)
            .build();

    Transition duplicate = new TransitionBuilder()
            .event(Event.DUPLICATE)
            .eventHandler(eventHandlers.duplicate)
            .startState(states.listOfOrders)
            .endState(states.duplicate)
            .build();

    Transition duplicateBack = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.duplicate)
            .endState(states.listOfOrders)
            .build();

    Transition duplicateInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.duplicateInt)
            .startState(states.duplicate)
            .endState(states.listOfOrders)
            .build();

    Transition menu = new TransitionBuilder()
            .event(Event.MENU)
            .eventHandler(eventHandlers.menu)
            .startState(states.buyer)
            .endState(states.menu)
            .build();

    Transition menuOrder = new TransitionBuilder()
            .event(Event.MAKE_ORDER)
            .eventHandler(eventHandlers.makeOrder)
            .startState(states.menu)
            .endState(states.buyer)
            .build();

    //TODO Подтверждение удаления
    Transition menuBack = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.menu)
            .endState(states.buyer)
            .build();

    Transition menuInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.menuInt)
            .startState(states.menu)
            .endState(states.menu)
            .build();

    Transition delete = new TransitionBuilder()
            .event(Event.DELETE)
            .eventHandler(eventHandlers.delete)
            .startState(states.menu)
            .endState(states.delete)
            .build();

    Transition deleteBack = new TransitionBuilder()
            .event(Event.BACK)
            .startState(states.delete)
            .endState(states.menu)
            .build();

    Transition deleteInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.deleteInt)
            .startState(states.delete)
            .endState(states.menu)
            .build();

    //TODO Я лично хз, у меня view cart в документации
    // не написан вообще как функция,
    // так что делать или не делать
    // лежит строго на Иване

    public final Set<Transition> items = Set.of(
            buyer,
            buyerBack,
            orders,
            ordersback,
            cancel,
            cancelBack,
            cancelInt,
            duplicate,
            duplicateBack,
            duplicateInt,
            menu,
            menuOrder,
            menuBack,
            menuInt,
            delete,
            deleteBack,
            deleteInt
    );
}
