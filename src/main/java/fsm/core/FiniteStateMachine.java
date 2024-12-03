package fsm.core;

import fsm.cfg.Event;
import fsm.cfg.States;
import fsm.cfg.Transitions;
import utils.Constants;

import java.util.Set;

/**
 * Конечный автомат
 */
public class FiniteStateMachine {
    private State currentState;
    private final Set<State> states;
    private final Set<Transition> transitions;
    Transition lastTransition;

    public FiniteStateMachine() {
        this.states = new States().getStates();
        this.transitions = new Transitions().get();
    }

    /**
     * Переключает FSM между состояниями, если существует нужный переход
     * @param event Событие - команда, которую ввёл пользователь
     * @param messageText Текст, который ввёл пользователь
     * @param chatId ID пользователя
     * @return Текст для пользователя
     */
    public final synchronized String fire(final Event event, String messageText, long chatId){

        if (event == null) {
            throw new NullPointerException("event == null");
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
                    throw new NullPointerException("У " + transition + " нету EventHandler'a ");
                }
            }
        }
        return  "Такой команды нет(\n\n" +
                fire(Event.HELP, messageText, chatId);
    }

    /**
     * Возвращает текущее состояние автомата
     */
    public State getCurrentState(){
        return currentState;
    }

    public void setCurrentState(State newState){
        currentState = newState;
    }
}
