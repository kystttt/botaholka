package urfu;

import java.util.HashMap;

/**
 * Синглтон сохраняющий контекст каждого пользователя
 */
public enum UsersState {
    INSTANCE;

    private HashMap<Long, HashMap<String, String>> userContext = new HashMap<>();

    private UsersState(){}

    public void addUser(Long chat_id){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("branch", "");
        hashMap.put("previous_message", "");
        hashMap.put("state", "");
        hashMap.put("shop_basket", "");
        userContext.put(chat_id, hashMap);
    }

    public boolean hasUser(Long id){
        return userContext.get(id) == null;
    }

    public String getBranch(Long id){
        return userContext.get(id).get("branch");
    }


    public void setBranch(Long id, String branch){
        HashMap<String, String> hashMap = userContext.get(id);
        hashMap.put("branch", branch);
        userContext.put(id, hashMap);
    }
}
