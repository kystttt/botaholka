package fsm.core;

import java.util.Set;

/**
 * Билдер для FSM
 */
public class FiniteStateMachineBuilder {
    private final FiniteStateMachine finiteStateMachine;

    public FiniteStateMachineBuilder(final Set<State> states, final State initialState) {
        finiteStateMachine = new FiniteStateMachine(states, initialState);
    }

    private void registerTransition(final Transition transition) {
        finiteStateMachine.registerTransition(transition);
    }

    public FiniteStateMachineBuilder registerTransitions(final Set<Transition> transitions) {
        for (Transition transition : transitions) {
            registerTransition(transition);
        }
        return this;
    }

    public FiniteStateMachine build() {
        return finiteStateMachine;
    }
}
