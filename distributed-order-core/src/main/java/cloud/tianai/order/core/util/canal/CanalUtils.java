package cloud.tianai.order.core.util.canal;

import cloud.tianai.order.core.dataobject.OrderMasterDO;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/2 19:11
 * @Description: canal组件工具包
 */
public class CanalUtils {


    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        //格式化时间为 时间戳格式.
        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
        gsonBuilder.registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context) -> new JsonPrimitive(src.getTime()));
        gson = gsonBuilder.create();
    }

    public static CanalResultData converterForJson(String json) {
        CanalResultData canalResultData = gson.fromJson(json, CanalResultData.class);
        return canalResultData;
    }

    public static interface MysqlType {
        String INSERT = "INSERT";
        String UPDATE = "UPDATE";
        String DELETE = "DELETE";
        String CREATE = "CREATE";
    }

}
