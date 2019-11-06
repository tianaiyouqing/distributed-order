package cloud.tianai.order.core.util.canal;

import cloud.tianai.order.core.dataobject.OrderMasterDO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CanalResultData<T> {

    private long id;

    /** 数据库名称. */
    private String database;
    /** 表名. */
    private String table;
    private List<String> pkNames;
    private Boolean isDdl;
    /** type类型 INSERT  UPDATE  DELETE CREATE ALTER . */
    private MysqlEventType type;
    // binlog executeTime
    private Long es;
    // dml build timeStamp
    private Long ts;
    /** 执行的sql. */
    private String sql;
    /** sql类型. */
    private Map<String, Integer> sqlType;
    /** mysql类型. */
    private Map<String, String> mysqlType;
    /** 修改后的数据. */
    private T data;
    /** 如果是修改操作，显示修改前的数据. */
    private List<Map<String, String>> old;
}
