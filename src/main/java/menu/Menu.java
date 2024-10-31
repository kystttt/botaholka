package menu;

import java.util.HashMap;
import java.util.List;

/**
 * Меню
 * @param <S> Параметр для хранения названий блюд в меню
 * @param <I> Параметр для хранения цен блюд в меню
 */
public interface Menu<S, I> {

    /**
     * Возвращает цену блюда в меню по ее названию
     * @param foodItemName Название блюда в меню
     * @return Цена блюда в меню
     */
    I getCost(S foodItemName);

    /**
     * Добавляет блюдо в меню
     * @param foodItemName Название блюда
     * @param foodItemCost Цена блюда
     */
    void addFoodItem(S foodItemName, I foodItemCost);

    /**
     * Возвращает список со всеми названиями блюд в меню
     */
    List<S> getFoodNames();
}
