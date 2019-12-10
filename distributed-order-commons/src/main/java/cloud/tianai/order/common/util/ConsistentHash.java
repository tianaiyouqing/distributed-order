package cloud.tianai.order.common.util;

import lombok.Getter;

import java.util.*;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:24
 * @Description: 一致hash
 */

public class ConsistentHash {

    /**
     * key表示服务器的hash值，value表示服务器的名称
     */
    private SortedMap<Integer, String> sortedMap = new TreeMap<Integer, String>();
    @Getter
    private ArrayList<String> servers = new ArrayList<>(8);

    /** 默认的虚拟节点为10. */
    private Integer virtualNodeNum = 10;
    /** 虚拟节点标识. */
    private String virtualNodeAfterIdent = "[VIP][%s]";

    public ConsistentHash(Collection<String> servers) {
        this.servers.addAll(servers);
        for (String server : servers) {
            int hash = getHash(server.substring(server.lastIndexOf("_")));
            sortedMap.put(hash, server);
        }
    }

    public ConsistentHash(Collection<String> servers, Integer virtualNodeNum) {
        this.servers.addAll(servers);
        for (String server : servers) {
            String sub = server.substring(server.lastIndexOf("_"));
            for (Integer i = 0; i < virtualNodeNum; i++) {
                String str = sub + String.format(virtualNodeAfterIdent, i);
                int hash = getHash(str);
                sortedMap.put(hash, server);
            }
        }
    }

    public ConsistentHash() {

    }

    public void setServers(Collection<String> servers) {
        for (String server : servers) {
            int hash = getHash(server);
            sortedMap.put(hash, server);
        }
    }



    public static int getHash(String str) {
        int hash = (hash = str.hashCode()) ^ (hash >>> 16);
        return hash;
    }

    /**
     * 一致hash算法得到对应的node
     * @param node
     * @return
     */
    public String getServerForConsistentHash(String node) {
        // 得到带路由的结点的Hash值
        int hash = getHash(node);
        // 得到大于该Hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Integer i = sortedMap.firstKey();
            //返回对应的服务器
            return sortedMap.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Integer i = subMap.firstKey();
            //返回对应的服务器
            return subMap.get(i);
        }
    }

    /**
     * 普通的模运算得到对应的node
     * @param node node
     * @return
     */
    public String getDatabaseForMod(String node) {
        int hash = getHash(node);
        int serverIndex = (int) (Math.floor((double) hash / (double) servers.size())) & (servers.size() - 1);
        return servers.get(serverIndex);
    }

    public String getTableForMod(String node) {
        int hash = getHash(node);
        int serverIndex = hash & (servers.size() - 1);
        return servers.get(serverIndex);
    }


    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash(Arrays.asList("_0", "_1", "_2", "_3", "_4", "_5", "_6", "_7"));
        System.out.println(consistentHash.getTableForMod("74"));
        System.out.println(consistentHash.getDatabaseForMod("74"));

    }
}