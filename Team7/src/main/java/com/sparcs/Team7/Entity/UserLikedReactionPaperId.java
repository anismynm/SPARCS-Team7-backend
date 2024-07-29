package com.sparcs.Team7.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // Equals, HashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserLikedReactionPaperId implements Serializable {
    private String email;
    private Integer rpId;
}
