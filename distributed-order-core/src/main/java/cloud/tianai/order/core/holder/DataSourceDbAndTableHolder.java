package cloud.tianai.order.core.holder;

import cloud.tianai.order.core.util.ConsistentHash;

import java.util.Collection;
import java.util.LinkedHashSet;

public class DataSourceDbAndTableHolder {

    private static Collection<String> dbs = new LinkedHashSet<>(10);

    private static Collection<String> tables = new LinkedHashSet<>(10);

    private static boolean dbAddLock = false;
    private static boolean tableAddLock = false;

    public static void setDbs(Collection<String> newDbs) {
        dbs.addAll(newDbs);
    }
    public static void setTables(Collection<String> newTables) {
        tables.addAll(newTables);
    }


    public static void addDbName(String dbName) {
        if(dbAddLock) {
            throw new RuntimeException("DB已锁，不可做添加操作");
        }
        dbs.add(dbName);
    }

    public static void addTableName(String tableName) {
        if(tableAddLock) {
            throw new RuntimeException("table已锁，不可做添加操作");
        }
        tables.add(tableName);
    }

    public static String getDbForHash(String name) {
        lockDB();
        return GetConsistentHashForDB.CONSISTENT_HASH.getServer(name);
    }

    public static String getTableForHash(String name) {
        lockTable();
        return GetConsistentHashForTable.CONSISTENT_HASH.getServer(name);
    }

    private static void lockDB() {
        if(!dbAddLock) {
            dbAddLock = true;
        }
    }
    private static void lockTable() {
        if(!tableAddLock) {
            tableAddLock = true;
        }
    }

    public static class GetConsistentHashForDB {
        static final ConsistentHash CONSISTENT_HASH = new ConsistentHash(dbs);
    }

    public static class GetConsistentHashForTable {
        static final ConsistentHash CONSISTENT_HASH = new ConsistentHash(tables);
    }
}
