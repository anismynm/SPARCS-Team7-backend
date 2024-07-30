package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.ReactionPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionPaperRepository extends JpaRepository<ReactionPaper, Integer> {
    @Query(value = "select rp_id from reaction_paper order by like_count desc limit 3", nativeQuery = true)
    public List<String> MostLikedRp();
}
