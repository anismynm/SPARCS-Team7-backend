package com.sparcs.Team7.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class myLikedDTO {
    private List<String> myLikedRP;
    private String count;
}
