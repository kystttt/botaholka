package fsm.cfg;

import fsm.core.Transition;
import fsm.core.TransitionBuilder;

import java.util.Set;

/**
 * Инициализация всех {@link Transition} для {@link fsm.core.FiniteStateMachine}
 */
public class Transitions {
    EventHandlers eventHandlers = new EventHandlers();
    States states = new States();

    Transition start = new TransitionBuilder()
            .event(Event.START)
            .eventHandler(eventHandlers.startHelp)
            .startState(states.start)
            .endState(states.start)
            .build();

    Transition startHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.startHelp)
            .startState(states.start)
            .endState(states.start)
            .build();


    Transition buyerEntryPoint = new TransitionBuilder()
            .event(Event.BUYER)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.start)
            .endState(states.buyer)
            .build();

    Transition buyerBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.startHelp)
            .startState(states.buyer)
            .endState(states.start)
            .build();

    Transition buyerHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.buyer)
            .endState(states.buyer)
            .build();

    Transition ordersEntryPoint = new TransitionBuilder()
            .event(Event.ORDERS)
            .eventHandler(eventHandlers.listOfOrdersHelp)
            .startState(states.buyer)
            .endState(states.listOfOrders)
            .build();

    Transition ordersHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.listOfOrdersHelp)
            .startState(states.listOfOrders)
            .endState(states.listOfOrders)
            .build();

    Transition ordersback = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.listOfOrders)
            .endState(states.buyer)
            .build();

    Transition cancelEntryPoint = new TransitionBuilder()
            .event(Event.CANCEL)
            .eventHandler(eventHandlers.cancelHelp)
            .startState(states.listOfOrders)
            .endState(states.cancel)
            .build();

    Transition cancelHelp = new TransitionBuilder()
            .event(Event.CANCEL)
            .eventHandler(eventHandlers.cancelHelp)
            .startState(states.cancel)
            .endState(states.cancel)
            .build();

    Transition cancelBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.listOfOrdersHelp)
            .startState(states.cancel)
            .endState(states.listOfOrders)
            .build();

    Transition cancelInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.cancelInt)
            .startState(states.cancel)
            .endState(states.listOfOrders)
            .build();

    Transition duplicateEntryPoint = new TransitionBuilder()
            .event(Event.DUPLICATE)
            .eventHandler(eventHandlers.duplicateHelp)
            .startState(states.listOfOrders)
            .endState(states.duplicate)
            .build();

    Transition duplicateHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.duplicateHelp)
            .startState(states.duplicate)
            .endState(states.duplicate)
            .build();

    Transition duplicateBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.listOfOrdersHelp)
            .startState(states.duplicate)
            .endState(states.listOfOrders)
            .build();

    Transition duplicateInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.duplicateInt)
            .startState(states.duplicate)
            .endState(states.listOfOrders)
            .build();

    Transition menuEntryPoint = new TransitionBuilder()
            .event(Event.MENU)
            .eventHandler(eventHandlers.menuHelp)
            .startState(states.buyer)
            .endState(states.menu)
            .build();

    Transition menuHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.menuHelp)
            .startState(states.menu)
            .endState(states.menu)
            .build();

    Transition menuOrder = new TransitionBuilder()
            .event(Event.MAKE_ORDER)
            .eventHandler(eventHandlers.makeOrder)
            .startState(states.menu)
            .endState(states.buyer)
            .build();


    Transition menuBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.menu)
            .endState(states.buyer)
            .build();

    Transition menuInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.menuInt)
            .startState(states.menu)
            .endState(states.menu)
            .build();

    Transition deleteEntryPoint = new TransitionBuilder()
            .event(Event.DELETE)
            .eventHandler(eventHandlers.deleteHelp)
            .startState(states.menu)
            .endState(states.delete)
            .build();

    Transition deleteHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.deleteHelp)
            .startState(states.delete)
            .endState(states.delete)
            .build();

    Transition deleteBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.menuHelp)
            .startState(states.delete)
            .endState(states.menu)
            .build();

    Transition deleteInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.deleteInt)
            .startState(states.delete)
            .endState(states.menu)
            .build();

    /**
     * Возвращает набор всех {@link Transition}
     */
    public Set<Transition> get(){
        return  Set.of(
            start,
            buyerEntryPoint,
            buyerBack,
            ordersEntryPoint,
            ordersback,
            cancelEntryPoint,
            cancelBack,
            cancelInt,
            duplicateEntryPoint,
            duplicateBack,
            duplicateInt,
            menuEntryPoint,
            menuOrder,
            menuBack,
            menuInt,
            deleteEntryPoint,
            deleteBack,
            deleteInt,
            buyerHelp,
            ordersHelp,
            cancelHelp,
            menuHelp,
            duplicateHelp,
            deleteHelp,
            startHelp
        );
    }
}
