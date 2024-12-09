package fsm.cfg;

import fsm.core.Transition;
import fsm.core.TransitionBuilder;

import java.util.ArrayList;
import java.util.List;
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

    Transition ordersBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.listOfOrders)
            .endState(states.buyer)
            .build();

    Transition cancelEntryPoint = new TransitionBuilder()
            .event(Event.CANCEL_ORDER)
            .eventHandler(eventHandlers.cancelHelp)
            .startState(states.listOfOrders)
            .endState(states.cancel)
            .build();

    Transition cancelHelp = new TransitionBuilder()
            .event(Event.HELP)
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
            .endState(states.thechoice)
            .build();

    Transition choiceNo = new TransitionBuilder()
            .event(Event.NO)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.thechoice)
            .endState(states.buyer)
            .build();

    Transition choiceYes = new TransitionBuilder()
            .event(Event.YES)
            .eventHandler(eventHandlers.reviewHelp)
            .startState(states.thechoice)
            .endState(states.review)
            .build();

    Transition reviewBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.review)
            .endState(states.buyer)
            .build();

    Transition reviewText = new TransitionBuilder()
            .event(Event.TEXT)
            .eventHandler(eventHandlers.reviewText)
            .startState(states.review)
            .endState(states.rating)
            .build();

    Transition ratingHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.ratingHelp)
            .startState(states.rating)
            .endState(states.rating)
            .build();

    Transition ratingInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.rating)
            .startState(states.rating)
            .endState(states.endreview)
            .build();

    Transition endReview = new TransitionBuilder()
            .event(Event.REWRITE)
            .eventHandler(eventHandlers.reviewHelp)
            .startState(states.endreview)
            .endState(states.review)
            .build();

    Transition endReviewYes = new TransitionBuilder()
            .event(Event.YES)
            .eventHandler(eventHandlers.endReviewYes)
            .startState(states.endreview)
            .endState(states.buyer)
            .build();

    Transition endReviewDelete = new TransitionBuilder()
            .event(Event.DELETE)
            .eventHandler(eventHandlers.endReviewBack)
            .startState(states.endreview)
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
            .startState(states.cart)
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
            .endState(states.cart)
            .build();

    Transition deleteInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.deleteInt)
            .startState(states.delete)
            .endState(states.cart)
            .build();

    Transition sellerEntryPoint = new TransitionBuilder()
            .event(Event.SELLER)
            .eventHandler(eventHandlers.sellerHelp)
            .startState(states.start)
            .endState(states.seller)
            .build();

    Transition sellerHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.sellerHelp)
            .startState(states.seller)
            .endState(states.seller)
            .build();

    Transition cartEntryPoint = new TransitionBuilder()
            .event(Event.CART)
            .eventHandler(eventHandlers.cart)
            .startState(states.menu)
            .endState(states.cart)
            .build();

    Transition cartHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.cart)
            .startState(states.cart)
            .endState(states.cart)
            .build();

    Transition cartBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.menuHelp)
            .startState(states.cart)
            .endState(states.menu)
            .build();

    Transition sellerBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.startHelp)
            .startState(states.seller)
            .endState(states.start)
            .build();

    Transition sellerOrders = new TransitionBuilder()
            .event(Event.SELLER_ORDERS)
            .eventHandler(eventHandlers.sellerOrders)
            .startState(states.seller)
            .endState(states.sellerOrders)
            .build();

    Transition sellerOrdersHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.sellerOrders)
            .startState(states.sellerOrders)
            .endState(states.sellerOrders)
            .build();

    Transition sellerOrdersBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.sellerHelp)
            .startState(states.sellerOrders)
            .endState(states.seller)
            .build();

    Transition nextStatus = new TransitionBuilder()
            .event(Event.NEXT_STATUS)
            .eventHandler(eventHandlers.nextStatus)
            .startState(states.sellerOrder)
            .endState(states.nextStatus)
            .build();

    Transition nextStatusHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.nextStatus)
            .startState(states.nextStatus)
            .endState(states.nextStatus)
            .build();

    Transition nextStatusInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.nextStatusInt)
            .startState(states.nextStatus)
            .endState(states.sellerOrders)
            .build();

    Transition nextStatusBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.sellerOrders)
            .startState(states.nextStatus)
            .endState(states.sellerOrders)
            .build();

    Transition nextStatusIntError = new TransitionBuilder()
            .event(Event.ERROR)
            .eventHandler(eventHandlers.IntError)
            .startState(states.nextStatus)
            .endState(states.nextStatus)
            .build();

    Transition sellerOrderError = new TransitionBuilder()
            .event(Event.ERROR)
            .eventHandler(eventHandlers.IntError)
            .startState(states.sellerOrder)
            .endState(states.sellerOrder)
            .build();

    Transition sellerOrder = new TransitionBuilder()
            .event(Event.MAKE_ORDER)
            .eventHandler(eventHandlers.sellerOrder)
            .startState(states.sellerOrders)
            .endState(states.sellerOrder)
            .build();

    Transition sellerOrderHelp = new TransitionBuilder()
            .event(Event.HELP)
            .eventHandler(eventHandlers.sellerOrder)
            .startState(states.sellerOrder)
            .endState(states.sellerOrder)
            .build();

    Transition sellerOrderInt = new TransitionBuilder()
            .event(Event.INT)
            .eventHandler(eventHandlers.sellerOrderInt)
            .startState(states.sellerOrder)
            .endState(states.sellerOrder)
            .build();

    Transition sellerOrderBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.sellerOrder)
            .startState(states.sellerOrder)
            .endState(states.sellerOrders)
            .build();

    Transition allreviewsEntryPoint = new TransitionBuilder()
            .event(Event.ALLREVIEWS)
            .eventHandler(eventHandlers.allreviews)
            .startState(states.buyer)
            .endState(states.allreviews)
            .build();


    Transition allreviewsBack = new TransitionBuilder()
            .event(Event.BACK)
            .eventHandler(eventHandlers.buyerHelp)
            .startState(states.allreviews)
            .endState(states.buyer)
            .build();

    Transition allreviewsNext = new TransitionBuilder()
            .event(Event.NEXT)
            .eventHandler(eventHandlers.allreviews)
            .startState(states.allreviews)
            .endState(states.allreviews)
            .build();

    /**
     * Возвращает набор всех {@link Transition}
     */
    public List<Transition> get() {
        return new ArrayList<>(List.of(
                allreviewsNext,
                allreviewsBack,
                allreviewsEntryPoint,
                start,
                buyerEntryPoint,
                buyerBack,
                ordersEntryPoint,
                ordersBack,
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
                startHelp,
                sellerEntryPoint,
                sellerHelp,
                sellerBack,
                cartEntryPoint,
                cartHelp,
                cartBack,
                sellerOrders,
                sellerOrdersBack,
                nextStatus,
                nextStatusInt,
                sellerOrdersHelp,
                nextStatusHelp,
                nextStatusBack,
                sellerOrder,
                sellerOrderHelp,
                sellerOrderInt,
                sellerOrderBack,
                nextStatusIntError,
                sellerOrderError,
                choiceNo,
                choiceYes,
                reviewBack,
                ratingInt,
                endReview,
                endReviewDelete,
                reviewText,
                endReviewYes,
                ratingHelp
        ));
    }
}

