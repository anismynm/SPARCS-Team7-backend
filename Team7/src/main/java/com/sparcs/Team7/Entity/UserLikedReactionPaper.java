package com.sparcs.Team7.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "UserLikedReactionPaper")
@IdClass(UserLikedReactionPaperId.class)
public class UserLikedReactionPaper implements Serializable {

    @Id
    @JoinColumn(name = "email", nullable = false)
    private String email;

    @Id
    @JoinColumn(name = "rp_id", nullable = false)
    private Integer rpId;
}
