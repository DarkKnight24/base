package com.movie.base.interfaces;

import com.movie.base.dto.UserBaseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Mengcc
 */
@FeignClient(value = "UserBaseServer")
public interface UserClient {
    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("user/detail/{userId}")
    UserBaseDto getUserDetail(@PathVariable Long userId);
}
