package com.movie.base.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Utils {

    public static String MD5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }
}
