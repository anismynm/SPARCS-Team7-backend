package com.sparcs.Team7.Controller;

import com.sparcs.Team7.DTO.loginDTO;
import com.sparcs.Team7.Entity.User;
import com.sparcs.Team7.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/books")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        try {
            userService.save(user);
            response.put("code", "SU");
            response.put("message", "Success.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "DE");
            response.put("message", "DataBase Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register/email")
    public Map<String, String> registerUserEmail(@RequestParam String email) {
        Map<String, String> response = new HashMap<>();
        if (userService.isEmail(email)) {
            response.put("isEmail", "true");
        }
        else {
            response.put("isEmail", "false");
        }

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody loginDTO loginDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            User loginUser = userService.login(loginDTO);
            response.put("code", "SU");
            response.put("message", "Success.");
            response.put("User", loginUser.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", "Error");
            response.put("message", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}