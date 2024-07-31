package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.likeDTO;
import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.Service.ReactionPaperService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books/main")
public class ReactionPaperController {

    private final ReactionPaperService reactionPaperService;

    @Getter
    @Setter
    public static class RPclass {
        private String error;
        private String message;
        private String RP_id1;
        private String RP_id2;
        private String RP_id3;
    }

    @GetMapping()
    public RPclass MostLikedRP() {
        RPclass rpclass = new RPclass();
        try {
            List<String> MostLiked = reactionPaperService.MostLiked();
            rpclass.RP_id1 = "image_" + MostLiked.get(0);
            rpclass.RP_id2 = "image_" + MostLiked.get(1);
            rpclass.RP_id3 = "image_" + MostLiked.get(2);
        } catch (Exception e) {
            rpclass.setError("Error");
            rpclass.setMessage(e.getMessage());
        }

        return rpclass;
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getRPinfo(@RequestParam("rp_id") String RP_id) {
        Map<String, String> response = new HashMap<>();
        int RPkey = Integer.parseInt(RP_id.substring(3));
        try {
            rpinfoDTO infoList = reactionPaperService.getRPinfo(RPkey);
            response.put("RP_title", infoList.getTitle());
            response.put("RP_date", infoList.getDate());
            response.put("RP_text", infoList.getText());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/info/like")
    public ResponseEntity<Map<String, String>> RPlike(@RequestBody likeDTO likeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            reactionPaperService.likeRP(likeDTO);
            response.put("code", "SU");
            response.put("message", "liked Done.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "FA");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/info/like/cancel")
    public ResponseEntity<Map<String, String>> RPlikeCancel(@RequestBody likeDTO likeDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            reactionPaperService.likeRPcancel(likeDTO);
            response.put("code", "SU");
            response.put("message", "liked Cancel Done.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "FA");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
