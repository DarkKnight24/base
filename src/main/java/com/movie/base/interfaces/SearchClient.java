package com.movie.base.interfaces;

import java.net.UnknownHostException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SearchServer")
public interface SearchClient {
    
    @GetMapping("search/movie/{keyWord}")
    Object searchMovie(@PathVariable String keyWord)
        throws UnknownHostException;
}
