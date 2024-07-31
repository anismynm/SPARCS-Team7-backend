package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.likeDTO;
import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Entity.ReactionPaper;
import com.sparcs.Team7.Entity.UserLikedReactionPaper;
import com.sparcs.Team7.Entity.UserLikedReactionPaperId;
import com.sparcs.Team7.Repository.ReactionPaperRepository;
import com.sparcs.Team7.Repository.UserLikedReactionPaperRepository;
import com.sparcs.Team7.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReactionPaperService {
    private final ReactionPaperRepository reactionPaperRepository;
    private final UserLikedReactionPaperRepository userLikedReactionPaperRepository;
    private final UserRepository userRepository;

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

    public rpinfoDTO getRPinfo(int id) {
        ReactionPaper result = reactionPaperRepository.findByRpId(id);
        if (result != null) {
            String rpTitle = result.getRpTitle();
            LocalDateTime rpDate = result.getRpDate();
            String rpText = result.getRpText();
            return new rpinfoDTO(rpTitle, rpDate, rpText);
        } else {
            return null; // 결과가 없을 경우 null 반환
        }
    }

    public void saveRP(rpsaveDTO rpsaveDTO) {
        ReactionPaper rp = new ReactionPaper();
        rp.setRpTitle(rpsaveDTO.getRpTitle());
        rp.setEmail(rpsaveDTO.getEmail());
        rp.setRpText(rpsaveDTO.getRpText());
        rp.setBookTitle(rpsaveDTO.getBookTitle());
        reactionPaperRepository.save(rp);
    }

    public void likeRP(likeDTO likeDTO) {
        UserLikedReactionPaper userLikedReactionPaper = new UserLikedReactionPaper();
        int rp_id = Integer.parseInt(likeDTO.getRp_id().substring(3));
        userLikedReactionPaper.setEmail(likeDTO.getEmail());
        userLikedReactionPaper.setRpId(rp_id);

        userLikedReactionPaperRepository.save(userLikedReactionPaper);
        reactionPaperRepository.like(rp_id);
    }

    public void likeRPcancel(likeDTO likeDTO) {
        UserLikedReactionPaperId userLikedReactionPaperId = new UserLikedReactionPaperId();
        int rp_id = Integer.parseInt(likeDTO.getRp_id().substring(3));
        userLikedReactionPaperId.setEmail(likeDTO.getEmail());
        userLikedReactionPaperId.setRpId(rp_id);

        userLikedReactionPaperRepository.deleteById(userLikedReactionPaperId);
        reactionPaperRepository.likeCancel(rp_id);
    }
}
