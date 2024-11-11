package menu;

import java.util.List;

/**
 * Меню
 */
public interface Menu {

    /**
     * Возвращает цену блюда в меню по ее названию
     * @param foodItemName Название блюда в меню
     * @return Цена блюда в меню
     */
    Integer getCost(String foodItemName);

    /**
     * Добавляет блюдо в меню
     * @param foodItemName Название блюда
     * @param foodItemCost Цена блюда
     */
    void addFoodItem(String foodItemName, Integer foodItemCost);

    /**
     * Возвращает список со всеми названиями блюд в меню
     */
    List<String> getFoodNames();

    /**
     * Возвращает названию блюда по его порядковому номеру
     * @param index Порядковый номер
     */
    String getName(Integer index);
}
