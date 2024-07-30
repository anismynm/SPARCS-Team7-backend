package com.sparcs.Team7.Service;

import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MyPageService {
    private final ReactionPaperRepository reactionPaperRepository;

    public MyPageService(ReactionPaperRepository reactionPaperRepository) {
        this.reactionPaperRepository = reactionPaperRepository;
    }

    public void deleteRP(int id) {
        reactionPaperRepository.deleteById(id);
    }

    public List<String> getMyRP(String email) {
        return reactionPaperRepository.findByEmail(email);
    }
}
