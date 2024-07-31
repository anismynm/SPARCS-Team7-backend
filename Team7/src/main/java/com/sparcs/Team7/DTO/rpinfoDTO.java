package com.sparcs.Team7.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class rpinfoDTO {
    private String title;
    private String date;
    private String text;

    public rpinfoDTO(String rpTitle, LocalDateTime rpDate, String rpText) {
        this.title = rpTitle;
        this.date = rpDate.toString(); // LocalDateTime을 String으로 변환
        this.text = rpText;
    }
}
