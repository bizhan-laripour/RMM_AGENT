package com.submodule.feign;

import com.submodule.dto.ZabbixResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "worker")
public interface WorkerFeign {
    @PostMapping(value = "/find-by-ip")
    ZabbixResponseDto getFromWorker(@RequestParam(name="ip") String ip);

}
