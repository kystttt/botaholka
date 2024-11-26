package fsm.cfg;

/**
 * Набор возможных команд для {@link fsm.core.FiniteStateMachine}
 */
public enum Event {
    // Для тестов
    PUSH,
    COIN,

    // Для использования
    BACK,
    BUYER,
    ORDERS,
    MENU,
    CANCEL_ORDER,
    DUPLICATE,
    INT,
    MAKE_ORDER,
    DELETE,
    SELLER,
    START,
    ERROR,
    HELP,
    CART,
    SELLER_ORDERS,
    NEXT_STATUS,

}
