package fsm.core;

/**
 * Состояние FSM
 */
public record State(String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return name.equals(state.name);

    }
}
