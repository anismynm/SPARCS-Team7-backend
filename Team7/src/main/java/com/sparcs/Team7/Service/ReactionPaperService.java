package com.sparcs.Team7.Service;

import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReactionPaperService {
    private final ReactionPaperRepository reactionPaperRepository;

    public ReactionPaperService(ReactionPaperRepository reactionPaperRepository) {
        this.reactionPaperRepository = reactionPaperRepository;
    }

    public static class NoDataFoundException extends RuntimeException {
        public NoDataFoundException(String message) {
            super(message);
        }
    }

    public List<String> MostLiked() {
        List<String> mostLikedList = reactionPaperRepository.MostLikedRp();
        if (mostLikedList == null || mostLikedList.isEmpty()) {
            throw new NoDataFoundException("No liked reaction papers found.");
        }
        return mostLikedList;
    }
}
