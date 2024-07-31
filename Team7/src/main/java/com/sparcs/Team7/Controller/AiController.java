package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.promptDTO;
import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Entity.ReactionPaper;
import com.sparcs.Team7.Service.AiService;
import com.sparcs.Team7.Service.BookService;
import com.sparcs.Team7.Service.NcpService;
import com.sparcs.Team7.Service.ReactionPaperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books/new")
public class AiController {
    private final AiService aiService;
    private final NcpService ncpService;
    private final BookService bookService;
    private final ReactionPaperService reactionPaperService;
    private String temp_prompt;
    private int temp_key;

    @GetMapping
    public ResponseEntity<Map<String, String>> getImage(@RequestParam String final_prompt)
            throws IOException, InterruptedException {
        System.out.println("final_prompt :" + final_prompt);
        String prompt = aiService.generatePictureV2(final_prompt);
        Integer image_key = ncpService.saveImageFromUrl(prompt);

        // 재생성을 위한 prompt, key 값 저장
        temp_prompt = final_prompt;
        temp_key = image_key;

        log.info("Bucket uploaded Successfully.");

        Map<String, String> response = new HashMap<>();
        response.put("code", "SU");
        response.put("message1", "send to Dall.e successfully.");
        response.put("message2", "Uploaded to Bucket Successfully.");
        response.put("image_id", "image_" + image_key.toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    public void RegenerateImage() throws IOException, InterruptedException {
        ncpService.deleteImageFromBucket(temp_key);
        getImage(temp_prompt);
    }

    @GetMapping("/synop")
    public ResponseEntity<Map<String, String>> getSynop(@RequestParam String book_title) {
        Map<String, String> response = new HashMap<>();
        response.put("code", "SU");
        response.put("synop", bookService.getSynop(book_title));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveRP(@RequestBody rpsaveDTO rpsavedto) {
        Map<String, String> response = new HashMap<>();
        try {
            reactionPaperService.saveRP(rpsavedto);
            response.put("code", "SU");
            response.put("message", "successfully saved.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "FAIL");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
