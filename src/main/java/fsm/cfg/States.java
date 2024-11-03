package fsm.cfg;

import fsm.core.State;

public class States {
    public State start = new State("start");
    public State buyer = new State("buyer");
    public State menu = new State("menu");
    public State listOfOrders = new State("orders");
    public State duplicate = new State("duplicate");
    public State cancel = new State("cancel");
    public State delete = new State("delete");
}
