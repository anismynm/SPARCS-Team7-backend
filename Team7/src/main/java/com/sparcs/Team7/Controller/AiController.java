package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.promptDTO;
import com.sparcs.Team7.Service.AiService;
import com.sparcs.Team7.Service.BookService;
import com.sparcs.Team7.Service.NcpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public ResponseEntity<Map<String, String>> getImage(@RequestParam String final_prompt)
            throws IOException, InterruptedException {
        System.out.println("final_prompt :" + final_prompt);
        String prompt = aiService.generatePictureV2(final_prompt);
        Integer image_key = ncpService.saveImageFromUrl(prompt);

        log.info("Bucket uploaded Successfully.");

        Map<String, String> response = new HashMap<>();
        response.put("code", "SU");
        response.put("message1", "send to Dall.e successfully.");
        response.put("message2", "Uploaded to Bucket Successfully.");
        response.put("image_id", "image_" + image_key.toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/synop")
    public ResponseEntity<Map<String, String>> getSynop(@RequestParam String book_title) {
        Map<String, String> response = new HashMap<>();
        response.put("code", "SU");
        response.put("synop", bookService.getSynop(book_title));

        return ResponseEntity.ok(response);
    }
}
