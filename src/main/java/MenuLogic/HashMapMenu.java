package MenuLogic;

import java.util.HashMap;

/**
 * Класс для функций меню
 * @param <S> Параметр для хранения названий блюд в меню
 * @param <I> Параметр для хранения цен блюд в меню
 */
public abstract class HashMapMenu<S, I> {
    /**
     * Список в котором хранятся названия блюд и их цена
     */
    HashMap<S, I> menuList;

    /**
     * Возвращает цену блюда в меню по ее названию
     * @param foodItemName Название блюда в меню
     * @return Цена блюда в меню
     */
    abstract I getCost(S foodItemName);

    /**
     * Добавляет блюдо в меню
     * @param foodItemName Название блюда
     * @param foodItemCost Цена блюда
     */
    public void addFoodItem(S foodItemName, I foodItemCost){
        menuList.put(foodItemName, foodItemCost);
    }

    /**
     * Геттер для меню
     * @return HashMap в котором хранится меню
     */
    public HashMap<S, I> getHashMap(){
        return menuList;
    }
}
