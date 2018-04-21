/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

/**
 *
 * @author Daniela
 */
public class JSONUtils {
    public static HashMap<String, String> jsonToHashString(JSONArray json) throws JSONException {
        HashMap<String, String> pairs = new HashMap<String, String>();
        
        for (int i = 0; i < json.size(); i++) {
           JSONObject j = (JSONObject)json.get(i);
           Set<String> s = j.keySet();
           
           for(String key : s){
               String value = (String) j.get(key);
               pairs.put(key, value);
           }
        }
        
        return pairs;
    }
}
