package MenuLogic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Menu extends HashMapMenu<String, Integer>{
    public Menu(String fileName){
        menuList = new HashMap<>();
        readFromJSONFile(fileName);
    }

    public Menu(){
        menuList = new HashMap<>();
    }

    /**
     * Возвращает цену блюда в меню по ее названию
     *
     * @param foodItemName Название блюда в меню
     * @return Цена блюда в меню
     */
    @Override
    public Integer getCost(String foodItemName) {
        if(menuList.get(foodItemName) == null){
            throw new NullPointerException("Такого элемента в меню нету");
        }
        return menuList.get(foodItemName);
    }

    /**
     * Считывает данные из заданного файла и возвращает HashMap
     * @param fileName Имя файла из которого считывается
     */
    private void readFromJSONFile(String fileName){
        try(FileReader file = new FileReader(fileName)) {
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(file);
            for (Object s : jsonObject.keySet()) {
                menuList.put((String)s, (int)jsonObject.get(s));
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
