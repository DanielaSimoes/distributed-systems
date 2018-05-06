package settings;

import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

/**
 * This file contains the JSONutils.
 * @author Daniela Simões, 76771
 */
public class JSONUtils {
    public static HashMap<String, String> jsonToHashString(JSONArray json) throws JSONException {
        HashMap<String, String> pairs = new HashMap<>();
        
        for (int i = 0; i < json.size(); i++) {
           JSONObject j = (JSONObject)json.get(i);
           Set<String> s = j.keySet();
           
           s.forEach((key) -> {
               String value = (String) j.get(key);
               pairs.put(key, value);
            });
        }
        
        return pairs;
    }
}
