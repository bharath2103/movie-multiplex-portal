package utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class ResponseDesigner {
    public static ObjectNode design(Object response, boolean status){
        ObjectNode result = Json.newObject();
        // add key:value pair
        result.put("isSuccessful", status);
        if(response instanceof String)
            result.put("body", (String)response);
        else
            result.putPOJO("body", response); // can serialize object

        return result;
    }
}
