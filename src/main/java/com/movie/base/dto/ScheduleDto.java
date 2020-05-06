package com.movie.base.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ScheduleDto {
    
    private Long scheduleId;
    
    private Long movieId;
    
    private String movieName;
    
    private String hallName;
    
    private String cinemaName;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    
    private double price;
    
    private int remainingSeat;
    
    private MovieDetailDto movieDto;
}
