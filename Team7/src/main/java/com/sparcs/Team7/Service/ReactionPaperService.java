package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.Entity.ReactionPaper;
import com.sparcs.Team7.Repository.ReactionPaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<Object[]> result = reactionPaperRepository.findByRpId(id);
        if (result != null) {
            String rpTitle = result.get(0)[0].toString();
            LocalDateTime rpDate = (LocalDateTime) result.get(0)[1];
            String rpText = (String) result.get(0)[2];
            return new rpinfoDTO(rpTitle, rpDate, rpText);
        } else {
            return null; // 결과가 없을 경우 null 반환
        }
    }
}
