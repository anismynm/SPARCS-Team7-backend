package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.loginDTO;
import com.sparcs.Team7.Entity.User;
import com.sparcs.Team7.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean isEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public static class NotCorrespondingEmailException extends RuntimeException {
        public NotCorrespondingEmailException(String message) {
            super(message);
        }
    }

    public User login(loginDTO loginDTO) {
        Optional<User> findUser = userRepository.findByEmail(loginDTO.getEmail());
        if(!findUser.orElseThrow(()-> new NotCorrespondingEmailException("해당 이메일이 존재하지 않습니다."))
                .checkPassword(loginDTO.getPassword())){
            throw new IllegalStateException("이메일과 비밀번호가 일치하지 않습니다.");
        }

        return findUser.get();
    }
}
