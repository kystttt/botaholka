package fsm.core;

import fsm.cfg.Event;

import java.util.HashSet;
import java.util.Set;

public class FiniteStateMachine {
    private State currentState;
    private final Set<State> finalStates;
    private final Set<State> states;
    private final Set<Transition> transitions;

    public FiniteStateMachine(Set<State> states, State initialState) {
        currentState = initialState;
        this.finalStates = new HashSet<>();
        this.states = states;
        this.transitions = new HashSet<>();
    }

    public final synchronized void fire(final Event event) {

        if (!finalStates.isEmpty() && finalStates.contains(currentState)) {
            return;
        }

        if (event == null) {
            return;
        }

        for (Transition transition : transitions) {
            if (currentState.equals(transition.getStartState()) &&
                            transition.getEvent().equals(event) &&
                            states.contains(transition.getEndState())
            ) {
                currentState = transition.getEndState();
                if(transition.getEventHandler() != null){
                    transition.getEventHandler().handleEvent();
                }
                break;
            }
        }
    }

    void registerTransition(final Transition transition) {
        transitions.add(transition);
    }

    public State getCurrentState() {
        return currentState;
    }
}
