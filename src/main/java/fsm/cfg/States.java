package fsm.cfg;

import fsm.core.State;

import java.util.Set;

/**
 * Инициализация всех {@link State} для {@link fsm.core.FiniteStateMachine}
 */
public class States {
    public State start = new State("start");
    public State buyer = new State("buyer");
    public State menu = new State("menu");
    public State listOfOrders = new State("orders");
    public State duplicate = new State("duplicate");
    public State cancel = new State("cancel");
    public State delete = new State("delete");
    public State seller = new State("seller");
    public State cart = new State("cart");
    public State sellerOrders = new State("sellerOrders");
    public State nextStatus = new State("nextStatus");
    public State sellerOrder = new State("sellerOrder");
    public State thechoice = new State("thechoice");
    public State review = new State("review");
    public State rating = new State("rating");
    public State endreview = new State("endreview");
    public State allreviews = new State("allreviews");

    public Set<State> getStates(){
        return Set.of(
                start,
                buyer,
                menu,
                listOfOrders,
                duplicate,
                cancel,
                delete,
                seller,
                cart,
                sellerOrders,
                nextStatus,
                sellerOrder,
                thechoice,
                review,
                rating,
                endreview,
                allreviews
        );
    }
}
