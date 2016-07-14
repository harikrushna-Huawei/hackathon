package org.onosproject.pcep.controller.impl;

import org.onlab.packet.IpAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by root1 on 29/6/16.
 */
final class PcepFlushTimerMap {

    static final long FLUSH_TIMER_INTERVAL = 5;

    private static final Logger log = LoggerFactory.getLogger(PcepFlushTimerMap.class);
    private static Map<IpAddress, ScheduledExecutorService> flushTimerMap =
                                                new HashMap<IpAddress, ScheduledExecutorService>();

    private PcepFlushTimerMap() {

    }

    public static void add(IpAddress pccIp) {
        // remove timer if exists already
        remove(pccIp);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        flushTimerMap.put(pccIp, executor);
        executor.schedule(new PcepFlushTimer(pccIp), FLUSH_TIMER_INTERVAL, TimeUnit.MINUTES);
        log.info("Flush Timer started for Pcc: " + pccIp.toString());
    }

    public static void remove(IpAddress pccIp) {
        if (flushTimerMap.containsKey(pccIp)) {
            flushTimerMap.get(pccIp).shutdown();
            flushTimerMap.remove(pccIp);
            log.info("Flush Timer stopped for Pcc: " + pccIp.toString());
        }
    }

    public static ScheduledExecutorService get(IpAddress pccIp) {
        if (flushTimerMap.containsKey(pccIp)) {
            return flushTimerMap.get(pccIp);
        }

        return null;
    }

    public static void cleanUp() {
        Iterator<Map.Entry<IpAddress, ScheduledExecutorService>> it = flushTimerMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<IpAddress, ScheduledExecutorService> entry = it.next();
            entry.getValue().shutdown();
            it.remove();
        }
    }

    private static class PcepFlushTimer implements Runnable {
        IpAddress pccIp = null;

        public PcepFlushTimer(IpAddress pccIp) {
            this.pccIp = pccIp;
        }

        @Override
        public void run() {
            log.info("Flush Timer callback for Pcc: " + pccIp.toString());
            PcepLabelDbVerManager.removeDbVersion(this.pccIp);
            PcepFlushTimerMap.remove(this.pccIp);
        }
    }
}
