package cloud.tianai.order.core.util.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title: GsonUtils
 * Description: 谷歌json工具 gson
 *
 * @author: 天爱有情
 * @date: 2018/6/26 15:54
 **/
public class GsonUtils {

    /**
     * gson
     */
    public static Gson GSON;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //格式化时间为 时间戳格式.
        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime()));
        GSON = gsonBuilder.create();
    }

    /**
     * 将对象转成json格式
     *
     * @param object 要转换成json的数据
     * @return String 返回的json
     */
    public static String gsonString(Object object) {
        return GSON.toJson(object);
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param json json
     * @param cls  class类型
     * @return 返回的数据
     */
    public static <T> T gsonToBean(String json, Class<T> cls) {
        return GSON.fromJson(json, cls);
    }

    /**
     * json字符串转成list
     *
     * @param json  json
     * @param clazz 转换的List中的类型
     * @return 返回转换好的数据
     */
    public static <T> List<T> gsonToList(String json, Class<T> clazz) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(GSON.fromJson(elem, clazz));
        }
        return mList;
    }

    /**
     * gsonToObject
     *
     * @param json   json
     * @param typeOf type
     * @return 返回 Object
     */
    public static <T> T gsonToObject(String json, Type typeOf) {
        return GSON.fromJson(json, typeOf);
    }

    /**
     * 获取type类型
     *
     * @param <T> 要转换的类型
     * @return Type
     */
    public static <T> Type getType() {
        return new TypeToken<T>() {
        }.getType();
    }
}