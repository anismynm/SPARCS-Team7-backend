package com.sparcs.Team7.Service;

import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainService {
    private final ReactionPaperRepository reactionPaperRepository;

    public MainService(ReactionPaperRepository reactionPaperRepository) {
        this.reactionPaperRepository = reactionPaperRepository;
    }
}
