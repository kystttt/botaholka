package fsm.core;

import fsm.cfg.Event;

public class TransitionBuilder {
    private final Transition transition;

    public TransitionBuilder(){
        transition = new Transition();
    }

    public TransitionBuilder startState(State state){
        transition.setStartState(state);
        return this;
    }

    public TransitionBuilder endState(State state){
        transition.setEndState(state);
        return this;
    }

    public TransitionBuilder event(Event event){
        transition.setEvent(event);
        return this;
    }

    public TransitionBuilder eventHandler(EventHandler eventHandler){
        transition.setEventHandler(eventHandler);
        return this;
    }

    public Transition build(){return transition;}


}
