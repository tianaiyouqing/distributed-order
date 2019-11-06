package cloud.tianai.order.core.util.canal;

import cloud.tianai.order.core.util.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 23:20
 * @Description: canal使用 mysql是eventType
 */
@Getter
@AllArgsConstructor
public enum MysqlEventType implements CodeEnum {


    /**
     * <code>INSERT = 1;</code>
     */
    INSERT(0, 1),
    /**
     * <code>UPDATE = 2;</code>
     */
    UPDATE(1, 2),
    /**
     * <code>DELETE = 3;</code>
     */
    DELETE(2, 3),
    /**
     * <code>CREATE = 4;</code>
     */
    CREATE(3, 4),
    /**
     * <code>ALTER = 5;</code>
     */
    ALTER(4, 5),
    /**
     * <code>ERASE = 6;</code>
     */
    ERASE(5, 6),
    /**
     * <code>QUERY = 7;</code>
     */
    QUERY(6, 7),
    /**
     * <code>TRUNCATE = 8;</code>
     */
    TRUNCATE(7, 8),
    /**
     * <code>RENAME = 9;</code>
     */
    RENAME(8, 9),
    /**
     * <code>CINDEX = 10;</code>
     *
     * <pre>
     * *CREATE INDEX*
     * </pre>
     */
    CINDEX(9, 10),
    /**
     * <code>DINDEX = 11;</code>
     */
    DINDEX(10, 11),
    /**
     * <code>GTID = 12;</code>
     */
    GTID(11, 12),
    /**
     * <code>XACOMMIT = 13;</code>
     *
     * <pre>
     * * XA *
     * </pre>
     */
    XACOMMIT(12, 13),
    /**
     * <code>XAROLLBACK = 14;</code>
     */
    XAROLLBACK(13, 14),
    /**
     * <code>MHEARTBEAT = 15;</code>
     *
     * <pre>
     * * MASTER HEARTBEAT *
     * </pre>
     */
    MHEARTBEAT(14, 15),
    ;
    private Integer code;
    private Integer value;

}