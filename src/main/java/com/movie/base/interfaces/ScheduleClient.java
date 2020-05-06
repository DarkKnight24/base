package com.movie.base.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MovieDataServer")
public interface ScheduleClient {
    
    @GetMapping("/movie/schedule/detail/{scheduleId}")
    Object detail(@PathVariable Long scheduleId);
}
