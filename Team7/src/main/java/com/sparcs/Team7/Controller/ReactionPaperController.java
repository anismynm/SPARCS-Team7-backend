package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.rpinfoDTO;
import com.sparcs.Team7.Service.ReactionPaperService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/books/main")
public class ReactionPaperController {

    private final ReactionPaperService reactionPaperService;

    @Autowired
    public ReactionPaperController(ReactionPaperService reactionPaperService) {
        this.reactionPaperService = reactionPaperService;
    }

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
    public Map<String, String> getRPinfo(@RequestParam String RP_id) {
        Map<String, String> response = new HashMap<>();
        int RPkey = Integer.parseInt(RP_id.substring(3));
        System.out.println("RPkey: " + RPkey);
        rpinfoDTO infoList = reactionPaperService.getRPinfo(RPkey);
        response.put("RP_title", infoList.getTitle());
        response.put("RP_date", infoList.getDate());
        response.put("RP_text", infoList.getText());
        return response;
    }
}
