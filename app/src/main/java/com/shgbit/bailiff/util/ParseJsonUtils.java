package com.shgbit.bailiff.util;

import com.google.gson.Gson;

/**
 * 解析转化json
 */
public class ParseJsonUtils {

    private ParseJsonUtils instance;
    private Gson gson;

    private ParseJsonUtils() {
        gson = new Gson();
        if (instance != null) {
            throw new RuntimeException();
        }
    }

    public static ParseJsonUtils getInstance() {
        return ParseJsonUtilsHolder.intance;
    }

    private static class ParseJsonUtilsHolder {
        private static final ParseJsonUtils intance = new ParseJsonUtils();
    }

    /**
     * Json转化为Bean对象
     */
    public <T> T parseByGson(String json, Class<T> t) throws Exception {
//        GsonBuilder gb = new GsonBuilder();
//        gb.registerTypeAdapter(String.class, new StringConverter());
        if (gson == null)
            gson = new Gson();
        return gson.fromJson(json, t);
    }

    /**
     * Bean转化为Json
     */
    public String toJson(Object bean) {
        if (gson == null)
            gson = new Gson();
        String jsonStr = gson.toJson(bean);
        return gson.toJson(bean);
    }


//    public class StringConverter implements JsonSerializer<String>,
//            JsonDeserializer<String> {
//        public JsonElement serialize(String src, Type typeOfSrc,
//                                     JsonSerializationContext context) {
//            if (src == null) {
//                return new JsonPrimitive("");
//            } else {
//                return new JsonPrimitive(src.toString());
//            }
//        }
//
//        public String deserialize(JsonElement json, Type typeOfT,
//                                  JsonDeserializationContext context)
//                throws JsonParseException {
//            return json.getAsJsonPrimitive().getAsString();
//        }
//    }

}
