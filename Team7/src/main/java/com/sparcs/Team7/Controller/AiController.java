package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.bookDTO;
import com.sparcs.Team7.DTO.promptDTO;
import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Entity.Book;
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
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books/new")
public class AiController {
    private final AiService aiService;
    private final NcpService ncpService;
    private final BookService bookService;
    private final ReactionPaperService reactionPaperService;
    private bookDTO tempbookDTO;
    private int tempImageKey;

    @GetMapping()
    public ResponseEntity<Map<String, String>> getBooks() {
        Map<String, String> response = new HashMap<String, String>();
        List<String> bookNames = bookService.getBooks();
        int i = 0;
        for (String bookName : bookNames) {
            response.put(String.valueOf(i), bookName);
            i++;
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> getImage(@RequestBody bookDTO bookDTO)
            throws IOException, InterruptedException {
        tempbookDTO = new bookDTO();
        tempbookDTO.setBookTitle(bookDTO.getBookTitle());
        tempbookDTO.setColor(bookDTO.getColor());

        String book_prompt = bookService.getPrompt(bookDTO.getBookTitle());
        String color = bookDTO.getColor();
        String final_prompt = book_prompt + " 전체적 " + color + " 톤";

        System.out.println("final_prompt :" + final_prompt);
        String prompt = aiService.generatePictureV2(final_prompt);
        Integer image_key = ncpService.saveImageFromUrl(prompt);
        tempImageKey = image_key;

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
        ncpService.deleteImageFromBucket(tempImageKey);
        getImage(tempbookDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveRP(@RequestBody rpsaveDTO rpsavedto) throws IOException {
        Map<String, String> response = new HashMap<>();
        int id = Integer.parseInt(rpsavedto.getRpId().substring(3));
        String encodedImgUrl = rpsavedto.getImgUrl();
        if (!encodedImgUrl.equals("ai")) {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedImgUrl.getBytes());
            ncpService.saveMyImg(decodedBytes, id);
        }
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
