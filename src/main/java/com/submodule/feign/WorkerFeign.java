package com.submodule.feign;

import com.submodule.dto.ZabbixResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "feign-to worker", url = "http://127.0.0.1:8082")
public interface WorkerFeign {
    @PostMapping(value = "/find-by-ip")
    ZabbixResponseDto getFromWorker(String ip);

}
