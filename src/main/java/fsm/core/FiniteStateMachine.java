package fsm.core;

import fsm.cfg.Event;
import fsm.cfg.Transitions;

import java.util.HashSet;
import java.util.Set;

public class FiniteStateMachine {
    private State currentState;
    private final Set<State> states;
    private final Set<Transition> transitions;
    Transition lastTransition;

    public FiniteStateMachine(Set<State> states, State initialState) {

        currentState = initialState;
        this.states = states;
        this.transitions = new HashSet<>();
    }

    public final synchronized String fire(final Event event, String messageText, long chatId){

        if (event == null) {
            throw new NullPointerException("event == null");
        }

        if(event == Event.ERROR){
            return "Такой команды нет(\n\n" +
                    fire(Event.HELP, messageText, chatId);
        }

        for (Transition transition : transitions) {
            if (currentState.equals(transition.getStartState()) &&
                            transition.getEvent().equals(event) &&
                            states.contains(transition.getEndState())
            ) {
                currentState = transition.getEndState();
                lastTransition = transition;
                try{
                    return transition.getEventHandler().handleEvent(messageText, chatId);
                }
                catch (NullPointerException e) {
                    throw new NullPointerException("У " +
                            transition + " нету EventHandler'a ");
                }
            }
        }
        return fire(Event.ERROR, messageText, chatId);
    }

    void registerTransition(final Transition transition) {
        transitions.add(transition);
    }

    public State getCurrentState() {
        return currentState;
    }
}
