package com.sparcs.Team7.Controller;

import com.sparcs.Team7.Service.ReactionPaperService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class MainController {

    private final ReactionPaperService reactionPaperService;

    @Autowired
    public MainController(ReactionPaperService reactionPaperService) {
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

    @GetMapping("/main")
    public RPclass MostLikedRP() {
        RPclass rpclass = new RPclass();
        try {
            List<String> MostLiked = reactionPaperService.MostLiked();
            rpclass.RP_id1 = MostLiked.get(0);
            rpclass.RP_id2 = MostLiked.get(1);
            rpclass.RP_id3 = MostLiked.get(2);
        } catch (Exception e) {
            rpclass.setError("Error");
            rpclass.setMessage(e.getMessage());
        }

        return rpclass;
    }


}
