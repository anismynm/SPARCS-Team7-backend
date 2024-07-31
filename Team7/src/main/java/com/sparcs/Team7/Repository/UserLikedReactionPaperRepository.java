package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.UserLikedReactionPaper;
import com.sparcs.Team7.Entity.UserLikedReactionPaperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLikedReactionPaperRepository extends JpaRepository<UserLikedReactionPaper, UserLikedReactionPaperId> {
    @Query(value = "select rp_id from user_liked_reaction_paper where email = :email", nativeQuery = true)
    List<String> findByEmail(@Param("email") String email);
}
