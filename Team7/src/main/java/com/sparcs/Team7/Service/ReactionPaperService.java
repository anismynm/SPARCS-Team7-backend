package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Entity.ReactionPaper;
import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
