package org.onosproject.pcep.controller.impl;

import org.onlab.packet.IpAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root1 on 29/6/16.
 */
final class PcepLabelDbVerManager {

    static final long LABEL_DB_INIT_VERSION = 0;
    private static final Logger log = LoggerFactory.getLogger(PcepLabelDbVerManager.class);

    private static Map<IpAddress, Long> localDbVerMap = new HashMap<IpAddress, Long>();
    private static Map<IpAddress, Long> rcvDbVerMap = new HashMap<IpAddress, Long>();

    private PcepLabelDbVerManager() {

    }

    public static void initDbVersion(IpAddress pccIp) {
        log.info("init label db version for Pcc: " + pccIp.toString());
        localDbVerMap.put(pccIp, LABEL_DB_INIT_VERSION);
    }

    public static long getDbVersion(IpAddress pccIp) {
        if (localDbVerMap.containsKey(pccIp)) {
            return localDbVerMap.get(pccIp);
        } else {
            return 0;
        }
    }

    public static void incrDbVersion(IpAddress pccIp) {
        if (localDbVerMap.containsKey(pccIp)) {
            localDbVerMap.put(pccIp, localDbVerMap.get(pccIp) + 1);
            log.info("incrementing label db version for Pcc: " + pccIp.toString()
                             + " new version: " + localDbVerMap.get(pccIp));
        }
    }

    public static void removeDbVersion(IpAddress pccIp) {
        log.info("resetting label db version for Pcc: " + pccIp.toString());
        localDbVerMap.remove(pccIp);
    }

    public static void setRcvDbVersion(IpAddress pccIp, long version) {
        rcvDbVerMap.put(pccIp, version);
    }

    public static long getRcvDbVersion(IpAddress pccIp) {
        if (rcvDbVerMap.containsKey(pccIp)) {
            return rcvDbVerMap.get(pccIp);
        } else {
            return 0;
        }
    }

    public static void resetRcvDbVersion(IpAddress pccIp) {
        rcvDbVerMap.remove(pccIp);
    }
}
