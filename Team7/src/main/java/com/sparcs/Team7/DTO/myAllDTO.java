package com.sparcs.Team7.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class myAllDTO {
    private List<String> myLikedRP;
    private List<String> myRP;
    private String name;
    private String myLikedCount;
    private String myCount;
}
