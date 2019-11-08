package cloud.tianai.order.common.canal;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/2 19:11
 * @Description: canal组件工具包
 */
public class CanalUtils {


    private static Gson GSON;
    private static Gson LOWER_CASE_WITH_UNDERSCORES_GSON;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        // 创建转换下划线的Gson
        LOWER_CASE_WITH_UNDERSCORES_GSON = gsonBuilder.create();

        //格式化时间为 时间戳格式.
        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime()));
        GSON = gsonBuilder.create();
    }

    private static class LowerCaseWithDeserializer<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement jsonElement;
            // 过滤数组，把数组脱壳为对象
            if (json.isJsonArray()) {
                jsonElement = json.getAsJsonArray().get(0);
            } else {
                jsonElement = json;
            }
            String jsonAsString = jsonElement.toString();
            T t = LOWER_CASE_WITH_UNDERSCORES_GSON.fromJson(jsonAsString, new TypeToken<T>() {
            }.getType());
            return t;
        }
    }

    public static <T> CanalResultData<T> converterForJson(String json) {
        CanalResultData<T> canalResultData = GSON.fromJson(json, new TypeToken<CanalResultData<T>>() {
        }.getType());
        return canalResultData;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"address_desc\": \"xxx小区\",\n" +
                "            \"after_sales_status\": \"0\",\n" +
                "            \"area\": \"小店区\",\n" +
                "            \"bid\": \"0001\",\n" +
                "            \"buyer_name\": \"zhangsan\",\n" +
                "            \"buyer_phone\": \"133333333333\",\n" +
                "            \"channel_id\": \"qd:0001\",\n" +
                "            \"city\": \"太原市\",\n" +
                "            \"coupon_id\": \"1191328687274893330\",\n" +
                "            \"coupon_price\": \"150\",\n" +
                "            \"create_time\": \"2019-11-04 20:17:47\",\n" +
                "            \"custom_coupon_price\": \"0\",\n" +
                "            \"oid\": \"1119132868727489331423010\",\n" +
                "            \"order_amount\": \"35850\",\n" +
                "            \"order_status\": \"7\",\n" +
                "            \"order_type\": \"0\",\n" +
                "            \"pay_remark\": \"这是买家留言\",\n" +
                "            \"platform_id\": \"pt:0001\",\n" +
                "            \"platform_type\": \"1\",\n" +
                "            \"province\": \"山西\",\n" +
                "            \"street\": \"八一街\",\n" +
                "            \"uid\": \"1191328687274893323\",\n" +
                "            \"update_time\": \"2019-11-04 20:17:47\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"database\": \"distributed_order_2\",\n" +
                "    \"es\": 1572869867000,\n" +
                "    \"id\": 7046,\n" +
                "    \"isDdl\": false,\n" +
                "    \"mysqlType\": {\n" +
                "        \"address_desc\": \"varchar(255)\",\n" +
                "        \"after_sales_status\": \"int(5)\",\n" +
                "        \"area\": \"varchar(20)\",\n" +
                "        \"bid\": \"varchar(32)\",\n" +
                "        \"buyer_name\": \"varchar(30)\",\n" +
                "        \"buyer_phone\": \"varchar(20)\",\n" +
                "        \"channel_id\": \"varchar(20)\",\n" +
                "        \"city\": \"varchar(20)\",\n" +
                "        \"coupon_id\": \"bigint(20)\",\n" +
                "        \"coupon_price\": \"bigint(20)\",\n" +
                "        \"create_time\": \"timestamp\",\n" +
                "        \"custom_coupon_price\": \"bigint(20)\",\n" +
                "        \"external_order_id\": \"varchar(32)\",\n" +
                "        \"oid\": \"varchar(25)\",\n" +
                "        \"order_amount\": \"bigint(20)\",\n" +
                "        \"order_status\": \"int(5)\",\n" +
                "        \"order_type\": \"int(2)\",\n" +
                "        \"pay_remark\": \"varchar(255)\",\n" +
                "        \"payment_time\": \"timestamp(3)\",\n" +
                "        \"platform_id\": \"varchar(32)\",\n" +
                "        \"platform_type\": \"int(5)\",\n" +
                "        \"province\": \"varchar(20)\",\n" +
                "        \"refund_time\": \"timestamp(3)\",\n" +
                "        \"street\": \"varchar(20)\",\n" +
                "        \"uid\": \"varchar(32)\",\n" +
                "        \"update_time\": \"timestamp\"\n" +
                "    },\n" +
                "    \"pkNames\": [\n" +
                "        \"oid\"\n" +
                "    ],\n" +
                "    \"sql\": \"\",\n" +
                "    \"sqlType\": {\n" +
                "        \"address_desc\": 12,\n" +
                "        \"after_sales_status\": 4,\n" +
                "        \"area\": 12,\n" +
                "        \"bid\": 12,\n" +
                "        \"buyer_name\": 12,\n" +
                "        \"buyer_phone\": 12,\n" +
                "        \"channel_id\": 12,\n" +
                "        \"city\": 12,\n" +
                "        \"coupon_id\": -5,\n" +
                "        \"coupon_price\": -5,\n" +
                "        \"create_time\": 93,\n" +
                "        \"custom_coupon_price\": -5,\n" +
                "        \"external_order_id\": 12,\n" +
                "        \"oid\": 12,\n" +
                "        \"order_amount\": -5,\n" +
                "        \"order_status\": 4,\n" +
                "        \"order_type\": 4,\n" +
                "        \"pay_remark\": 12,\n" +
                "        \"payment_time\": 93,\n" +
                "        \"platform_id\": 12,\n" +
                "        \"platform_type\": 4,\n" +
                "        \"province\": 12,\n" +
                "        \"refund_time\": 93,\n" +
                "        \"street\": 12,\n" +
                "        \"uid\": 12,\n" +
                "        \"update_time\": 93\n" +
                "    },\n" +
                "    \"table\": \"order_master_6\",\n" +
                "    \"ts\": 1572870051049,\n" +
                "    \"type\": \"INSERT\"\n" +
                "}";

//        Object o = GSON.fromJson(json, new TypeToken<CanalResultData<List<Map<String,String>>>>() {
//        }.getType());
//        System.out.println(o);
        Object a = new Object();
        Object b = new Object();
        System.out.println((b = a) == a);
        System.out.println(a == (b = a));

    }
}
