package com.sparcs.Team7.Service;

import com.sparcs.Team7.Repository.ReactionPaperRepository;
import com.sparcs.Team7.Repository.UserLikedReactionPaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyPageService {
    private final ReactionPaperRepository reactionPaperRepository;
    private final UserLikedReactionPaperRepository userLikedReactionPaperRepository;

    public void deleteRP(int id) {
        reactionPaperRepository.deleteById(id);
    }

    public List<String> getMyRP(String email) {
        return reactionPaperRepository.findByEmail(email);
    }

    public List<String> getMyLikedRP(String email) {
        return userLikedReactionPaperRepository.findByEmail(email);
    }
}
