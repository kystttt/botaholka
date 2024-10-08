import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public enum MenuList {
    INSTANCE;
    private final String MENU_CONST = "Меню: \n";
    private final String CHOOSE_CONST = "Введите номер того, что хотите заказать: ";
    private HashMap<Integer, String> menulist;
    private ArrayList<String> cart;
    /**
     * Переменная, в которой хранится прошлая команда
     */
    private String prevcommand;
    MenuList() {
        menulist = new HashMap<>();
        cart = new ArrayList<>();
        prevcommand = "";
    }

    /**
     * Метод, который возвращает значение списка меню по индексу блюда
     * @param index
     * @return
     */
    public String getMenuListValue(int index) {return menulist.get(index);}

    /**
     * Возвращает хэш мапу, в которой находится меню с ключом и значением
     * @return
     */
    public HashMap<Integer, String> getMenulist() {return menulist;}

    /**
     * Возвращает значение корзины по ключу индекс блюда
     * @param index
     * @return
     */
    public String getCartValue(int index) {
        if (index >= 0 && index < cart.size()) {
            return cart.get(index);
        }
        return null;
    }

    /**
     * Возвращает корзину как список
     * @return
     */
    public ArrayList<String> getCart() {
        return cart;
    }

    /**
     * Добавляет в корзину
     * @param dish
     */
    public void addToCart(String dish) {
        cart.add(dish);
    }

    /**
     * Возвращает количество элементов в корзине
     * @return
     */
    public int getCartSize() {
        return cart.size(); // Возвращаем размер ArrayList
    }

    /**
     * Удаляет блюдо из корзины
     * @param index
     */
    public void removeFromCart(int index) {
        if (index >= 0 && index < cart.size()) {
            cart.remove(index); // Удаляем элемент по индексу
        }
    }

    /**
     * Возвращает предыдущую команду, введенную пользователем
     * @return
     */
    public String getPrevCommand(){return prevcommand;}

    /**
     * Присваивает значение в переменную, в которой хранится предыдущая команда
     * @param str
     */
    public void setPrevCommand(String str) { prevcommand = str;}

}

