package com.submodule.utils;

import com.submodule.entity.Threshold;
import com.submodule.enums.IpRanges;
import org.springframework.stereotype.Component;

import static com.submodule.enums.IpRanges.*;

@Component
public class IpUtil {

    private static final String BASE_IP_RANGE = "192.168.";


    public IpRanges chooseKafkaTopicForSendToWorker(Threshold threshold) throws Exception {
        String[] ipRange = threshold.getIp().split(BASE_IP_RANGE);
        if (ipRange[1].startsWith("25")) {
            return WORKER_ONE;
        } else if (ipRange[1].startsWith("26")) {
            return WORKER_TWO;
        } else if (ipRange[1].startsWith("27")) {
            return WORKER_THREE;
        } else {
            return UNKNOWN;
        }
    }


}
