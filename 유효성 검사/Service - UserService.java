package me.jaewonna.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.jaewonna.springbootdeveloper.domain.User;
import me.jaewonna.springbootdeveloper.dto.UserRequestDto;
import me.jaewonna.springbootdeveloper.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    /* 회원가입 시, 유효성 체크 */
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public User save(UserRequestDto request){
        return userRepository.save(request.toEntity());
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
