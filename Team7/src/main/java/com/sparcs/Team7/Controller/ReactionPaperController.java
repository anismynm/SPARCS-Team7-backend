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

    @GetMapping()
    public ResponseEntity<Map<String, String>> MostLikedRP() {
        Map<String, String> response = new HashMap<>();
        try {
            List<String> MostLiked = reactionPaperService.MostLiked();
            response.put("code", "null");
            response.put("message", "null");
            response.put("rp_id1", "image_" + MostLiked.get(0));
            response.put("rp_id2", "image_" + MostLiked.get(1));
            response.put("rp_id3", "image_" + MostLiked.get(2));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "Error");
            response.put("message", e.getMessage());
            response.put("rp_id1", "null");
            response.put("rp_id2", "null");
            response.put("rp_id3", "null");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getRPinfo(@RequestParam String RP_id) {
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
