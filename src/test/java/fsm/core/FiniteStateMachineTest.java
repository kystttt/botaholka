package fsm.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Тестирование перехода между состояниями в FSM
 */
public class FiniteStateMachineTest {
    FiniteStateMachine fsmTest;
    String tmplMessageText = "1";
    Long tmplChatId = 1L;

    State lock = new State("lock");
    State unlock = new State("unlock");

    private List<Transition> geTestTransitions() {
        return new ArrayList<>(List.of(
                new TransitionBuilder()
                        .event(fsm.cfg.Event.PUSH)
                        .startState(unlock)
                        .endState(lock)
                        .eventHandler((tmplMessageText, tmplChatId)-> "locked")
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.COIN)
                        .startState(lock)
                        .endState(unlock)
                        .eventHandler((tmplMessageText, tmplChatId)-> "unlocked")
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.HELP)
                        .startState(lock)
                        .endState(lock)
                        .eventHandler((tmplMessageText, tmplChatId)-> "in lock")
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.HELP)
                        .startState(unlock)
                        .endState(unlock)
                        .eventHandler((tmplMessageText, tmplChatId)-> "in unlock")
                        .build()
        ));
    }

    FiniteStateMachineTest(){
        fsmTest = new FiniteStateMachine(geTestTransitions(), Set.of(lock, unlock));
    }

    /**
     * Вызов Event который не изменяет состояние fsm
     */
    @Test
    public void fsmStaticTest(){
        fsmTest.setCurrentState(lock);

        String result = fsmTest.fire(fsm.cfg.Event.PUSH, tmplMessageText, tmplChatId);
        Assertions.assertEquals(lock, fsmTest.getCurrentState());
        Assertions.assertEquals("""
                Такой команды нет(
                
                in lock""", result);

        fsmTest.setCurrentState(unlock);

        result = fsmTest.fire(fsm.cfg.Event.COIN, tmplMessageText, tmplChatId);
        Assertions.assertEquals(unlock, fsmTest.getCurrentState());
        Assertions.assertEquals("""
                Такой команды нет(
                
                in unlock""", result);
    }

    /**
     * Вызов Event который изменяет состояние fsm
     */
    @Test
    public void fsmDynamicTest(){
        fsmTest.setCurrentState(lock);

        String result = fsmTest.fire(fsm.cfg.Event.COIN, tmplMessageText, tmplChatId);
        Assertions.assertEquals(unlock, fsmTest.getCurrentState());
        Assertions.assertEquals("unlocked", result);

        fsmTest.setCurrentState(unlock);

        result = fsmTest.fire(fsm.cfg.Event.PUSH, tmplMessageText, tmplChatId);
        Assertions.assertEquals(lock, fsmTest.getCurrentState());
        Assertions.assertEquals("locked", result);
    }
}
