package menu;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация Menu
 */
public class MenuImpl implements Menu{
    /**
     * Список в котором хранятся названия блюд и их цена
     */
    private final Map<String, Integer> items;

    public MenuImpl(String fileName) {
        items = new LinkedHashMap<>();
        readFromJSONFile(fileName);
    }

    public MenuImpl(){
        items = new LinkedHashMap<>();
    }


    @Override
    public Integer getCost(String foodItemName) {
        Integer cost = items.get(foodItemName);
        if(cost == null){
            return -1;
        }
        return cost;
    }

    @Override
    public void addFoodItem(String foodItemName, Integer foodItemCost) {
        items.put(foodItemName, foodItemCost);
    }

    @Override
    public List<String> getFoodNames() {
        return new ArrayList<>(items.keySet());
    }

    @Override
    public String getName(Integer index) {
        int i = 0;
        String lastName = "1";
        if(index > items.size()){
            return "-1";
        }
        for (String name : items.keySet()){
            if(i == index) break;
            lastName = name;
            i++;
        }
        return lastName;
    }

    /**
     * Считывает данные из заданного файла и возвращает HashMap
     * @param fileName Имя файла из которого считывается
     */
    private void readFromJSONFile(String fileName){
        try(FileReader file = new FileReader(fileName)) {
            JSONObject jsonObject = (JSONObject)new JSONParser().parse(file);
            for (Object s : jsonObject.keySet()) {
                items.put((String)s, ((Long) jsonObject.get(s)).intValue());
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в открытии файла", e);
        } catch (ParseException e) {
            throw new RuntimeException("Ошибка в чтении из JSON файла",e);
        }
    }
}
