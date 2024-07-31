package com.sparcs.Team7.Controller;

import com.sparcs.Team7.Service.MyPageService;
import com.sparcs.Team7.Service.NcpService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/books/my")
public class MyPageController {
    private final MyPageService myPageService;
    private final NcpService ncpService;

    private boolean MappingId(Map<String, String> response, List<String> rpList) {
        if (rpList.isEmpty()) {
            response.put("rp_id", "Empty");
            return false;
        }
        int i = 0;
        for (String rp : rpList) {
            response.put("rp_id" + i, rp);
            i++;
        }
        return true;
    }

    @GetMapping()
    public ResponseEntity<Map<String, String>> myRP(@RequestParam("email") String email) {
        Map<String, String> response = new HashMap<>();
        List<String> rpList = myPageService.getMyRP(email);
        if (MappingId(response, rpList)) return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/like")
    public ResponseEntity<Map<String, String>> myLikedRP(@RequestParam("email") String email) {
        Map<String, String> response = new HashMap<>();
        List<String> rpList = myPageService.getMyLikedRP(email);
        if (MappingId(response, rpList)) return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> myAllRP(@RequestParam("email") String email) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> rpMap = new HashMap<>();
        Map<String, String> likedRpMap = new HashMap<>();

        List<String> rpList = myPageService.getMyRP(email);
        List<String> likedRpList = myPageService.getMyLikedRP(email);
        MappingId(rpMap, rpList);
        MappingId(likedRpMap, likedRpList);
        response.put("myRP", rpMap);
        response.put("myLikedRP", likedRpMap);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestParam("rp_id") String rp_id) {
        Map<String, String> response = new HashMap<>();
        int RPkey = Integer.parseInt(rp_id.substring(3));
        myPageService.deleteRP(RPkey);
        ncpService.deleteImageFromBucket(RPkey);
        response.put("code", "SU");
        response.put("message", "deleted successfully");
        return ResponseEntity.ok(response);
    }
}
