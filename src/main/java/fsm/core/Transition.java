package fsm.core;

import fsm.cfg.Event;

public class Transition{
    private State startState;
    private State endState;
    private Event event;
    private EventHandler eventHandler;

    public State getStartState() {
        return startState;
    }

    void setStartState(State startState) {
        this.startState = startState;
    }

    public State getEndState() {
        return endState;
    }

    void setEndState(State endState) {
        this.endState = endState;
    }

    public Event getEvent() {
        return event;
    }

    void setEvent(Event event) {
        this.event = event;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public String toString(){
        return "Transition[" +
                "StartState: " + startState.name() + ", " +
                "EndState:" + endState.name() + ", " +
                "Event:" + event +"]";
    }
}
