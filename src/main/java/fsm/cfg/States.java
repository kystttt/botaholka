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
                nextStatus
        );
    }
}
