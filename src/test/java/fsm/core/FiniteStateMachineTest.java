package fsm.core;

import fsm.cfg.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Тестирование перехода между состояниями в FSM
 */
public class FiniteStateMachineTest {
    FiniteStateMachine fsmTest;
    String tmplMessageText = "1";
    Long tmplChatId = 1L;

    public enum Event{
        PUSH,
        COIN
    }

    State lock = new State("lock");
    State unlock = new State("unlock");

    private Set<Transition> geTestTransitions() {
        return Set.of(
                new TransitionBuilder()
                        .event(fsm.cfg.Event.PUSH)
                        .startState(unlock)
                        .endState(lock)
                        .eventHandler((tmplMessageText, tmplChatId)->{
                            return "locked";
                        })
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.COIN)
                        .startState(lock)
                        .endState(unlock)
                        .eventHandler((tmplMessageText, tmplChatId)->{
                            return "unlocked";
                        })
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.HELP)
                        .startState(lock)
                        .endState(lock)
                        .eventHandler((tmplMessageText, tmplChatId)->{
                            return "in lock";
                        })
                        .build(),

                new TransitionBuilder()
                        .event(fsm.cfg.Event.HELP)
                        .startState(unlock)
                        .endState(unlock)
                        .eventHandler((tmplMessageText, tmplChatId)->{
                            return "in unlock";
                        })
                        .build()
        );
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
