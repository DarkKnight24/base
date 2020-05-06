package com.movie.base.dto;

import lombok.Data;

/**
 * @作者 ：soul
 * @创建日期 ：Created in 2020/3/7 19:12 @描述：
 */
@Data
public class MovieDto {
    
    private Long movieId;
    
    private String moviePicture;
    
    private Double movieScore;
    
    private String movieCnName;
}
