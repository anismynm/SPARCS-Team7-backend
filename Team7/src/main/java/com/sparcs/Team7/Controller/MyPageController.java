package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.myAllDTO;
import com.sparcs.Team7.DTO.myLikedDTO;
import com.sparcs.Team7.DTO.myRPDTO;
import com.sparcs.Team7.Service.MyPageService;
import com.sparcs.Team7.Service.NcpService;
import jakarta.persistence.Tuple;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books/my")
public class MyPageController {
    private final MyPageService myPageService;
    private final NcpService ncpService;


    @GetMapping()
    public myRPDTO myRP(@RequestParam("email") String email) {
        myRPDTO response = new myRPDTO();
        List<String> rpList = myPageService.getMyRP(email);
        response.setMyRP(rpList);
        response.setCount(String.valueOf(rpList.size()));

        return response;
    }

    @GetMapping("/like")
    public myLikedDTO myLikedRP(@RequestParam("email") String email) {
        myLikedDTO response = new myLikedDTO();
        List<String> rpList = myPageService.getMyLikedRP(email);
        response.setMyLikedRP(rpList);
        response.setCount(String.valueOf(rpList.size()));

        return response;
    }

    @GetMapping("/all")
    public myAllDTO myAllRP(@RequestParam("email") String email) {
        myAllDTO response = new myAllDTO();
        List<String> myLikedList = myPageService.getMyLikedRP(email);
        List<String> myList = myPageService.getMyRP(email);
        response.setMyLikedRP(myLikedList);
        response.setMyRP(myList);
        response.setName(myPageService.getMyName(email));
        response.setMyLikedCount(String.valueOf(myLikedList.size()));
        response.setMyCount(String.valueOf(myList.size()));

        return response;
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
