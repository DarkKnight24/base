package com.movie.base.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MovieBaseServer")
public interface ScheduleClient {
    
    @GetMapping("/schedule/detail/{scheduleId}")
    Object detail(@PathVariable Long scheduleId);
}
