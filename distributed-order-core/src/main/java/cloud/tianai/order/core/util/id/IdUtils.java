package cloud.tianai.order.core.util.id;

import java.util.UUID;

/**
 * Title: IdUtils
 * Description: ID工具包
 *
 * @author: 天爱有情
 * @date: 2018/11/20 14:39
 **/
public class IdUtils {

    /**
     * 主机和进程的机器码
     */
    private static final Sequence worker = new Sequence();

    public static long getNoRepetitionId() {
        return worker.nextId();
    }

    public static String getNoRepetitionIdStr() {
        return String.valueOf(worker.nextId());
    }

    /**
     * <p>
     * 获取去掉"-" UUID
     * </p>
     */
    public static synchronized String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
