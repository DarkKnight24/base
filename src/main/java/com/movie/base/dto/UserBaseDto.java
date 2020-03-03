package com.movie.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mengcc
 */
@Data
public class UserBaseDto implements Serializable {

    private Long userId;

    private String userName;

    private Integer userRole;

    private String userEmail;

    private String userHeadImg;
}
