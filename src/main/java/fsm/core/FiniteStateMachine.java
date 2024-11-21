package fsm.core;

import fsm.cfg.Event;

import java.util.HashSet;
import java.util.Set;

/**
 * Конечный автомат
 */
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

    /**
     * Переключает FSM между состояниями, если существует нужный переход
     * @param event Событие - т.е. команда, которую ввёл пользователь
     * @param messageText Текст, который ввёл пользователь
     * @param chatId ID пользователя
     * @return Текст для пользователя
     */
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

    /**
     * Добавление перехода в FSM
     */
    void registerTransition(final Transition transition) {
        transitions.add(transition);
    }
}
