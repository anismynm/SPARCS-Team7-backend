package com.sparcs.Team7.Repository;

import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.Entity.ReactionPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReactionPaperRepository extends JpaRepository<ReactionPaper, Integer> {
    @Query(value = "select rp_id from reaction_paper order by like_count desc limit 3", nativeQuery = true)
    public List<String> MostLikedRp();

    @Query(value = "select count(*) from reaction_paper", nativeQuery = true)
    public int RPcount();

    @Query(value = "select rp_title, rp_date, rp_text from reaction_paper where rp_id = :id", nativeQuery = true)
    public List<Object[]> findByRpId(@Param("id") int rpId);
}
