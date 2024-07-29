package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.UserLikedReactionPaper;
import com.sparcs.Team7.Entity.UserLikedReactionPaperId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikedReactionPaperRepository extends JpaRepository<UserLikedReactionPaper, UserLikedReactionPaperId> {
}
